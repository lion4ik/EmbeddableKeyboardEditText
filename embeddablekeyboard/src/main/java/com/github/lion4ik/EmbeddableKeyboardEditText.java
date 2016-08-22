package com.github.lion4ik;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Alexey_Pushkarev1 on 07/12/2016.
 */
public class EmbeddableKeyboardEditText extends EditText {
    private EmbeddableKeyboard keyboard;
    private InputConnection keyboardConnection;
    private int keyboardResId;
    private boolean useSystemKeyboard;

    private OnFocusChangeListener focusChangeListener;
    private OnLongClickListener longClickListener;
    private OnClickListener clickListener;
    private OnTouchListener touchListener;

    private void init(final AttributeSet attrs) {
        keyboardConnection = new InputConnection() {
            @Override
            public void onKeyPressed(char symbol) {
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                int selection;
                String insertString = Character.toString(symbol);
                if (selectionStart == selectionEnd) {
                    setText(getText().insert(selectionStart, insertString).toString());
                    selection = selectionEnd + 1;
                } else {
                    selection = selectionStart + 1;
                    setText(getText().replace(selectionStart, selectionEnd, insertString));
                }

                int length = getText().length();
                if (selection > length) {
                    setSelection(length);
                } else {
                    setSelection(selection);
                }
            }

            @Override
            public void onBackspacePressed() {
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                int length = getText().length();
                if (length > 0) {
                    if (selectionStart == selectionEnd && selectionStart > 0) {
                        setText(getText().delete(selectionStart - 1, selectionStart));
                        setSelection(selectionStart - 1);
                    } else {
                        setText(getText().delete(selectionStart, selectionEnd));
                        setSelection(selectionStart);
                    }
                }
            }
        };

        if(attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmbeddableKeyboardEditText);
            try {
                String allowedCharacters = a.getString(R.styleable.EmbeddableKeyboardEditText_availableSymbols);
                keyboardResId = a.getResourceId(R.styleable.EmbeddableKeyboardEditText_keyboard, NO_ID);
                useSystemKeyboard = a.getBoolean(R.styleable.EmbeddableKeyboardEditText_useSystemKeyboard, false);
                setFilters(new InputFilter[]{new CharactersFilter(allowedCharacters)});
            } finally {
                a.recycle();
            }
        }

        setInputType(InputType.TYPE_NULL);
        setTextIsSelectable(true);
        setFocusableInTouchMode(true);

        longClickListener = new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setCursorVisible(true);
                return false;
            }
        };

        focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (keyboard != null) {
                    if (hasFocus) {
                        keyboard.showKeyboard();
                    } else {
                        keyboard.hideKeyboard();
                    }
                }
            }
        };

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCursorVisible(true);
                if (keyboard != null) {
                    keyboard.showKeyboard();
                }
            }
        };
        // Disable standard keyboard hard way
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                keyboard.showKeyboard();
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        };

        setUseSystemKeyboard(useSystemKeyboard);
    }

    public void setUseSystemKeyboard(boolean useSystemKeyboard) {
        if (useSystemKeyboard) {
            setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            setOnLongClickListener(null);
            setOnFocusChangeListener(null);
            setOnClickListener(null);
            // Disable standard keyboard hard way
            setOnTouchListener(null);
        } else {
            setInputType(InputType.TYPE_NULL);

            setOnLongClickListener(longClickListener);
            setOnFocusChangeListener(focusChangeListener);
            setOnClickListener(clickListener);
            // Disable standard keyboard hard way
            setOnTouchListener(touchListener);
        }
    }

    public void addFilter(InputFilter filter) {
        InputFilter[] oldFilters = getFilters();
        InputFilter[] newFilters = new InputFilter[oldFilters.length + 1];
        System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
        newFilters[oldFilters.length] = filter;
        setFilters(newFilters);
    }

    public void addFilters(InputFilter[] filters) {
        InputFilter[] oldFilters = getFilters();
        InputFilter[] newFilters = new InputFilter[oldFilters.length + filters.length];
        System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
        System.arraycopy(filters, 0, newFilters, oldFilters.length, filters.length);
        setFilters(newFilters);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (keyboardResId != NO_ID) {
            View keyboardView = getRootView().findViewById(keyboardResId);
            if (!(keyboardView instanceof KeyboardFrame)) {
                throw new IllegalArgumentException("view with id " + keyboardResId + "is not parent of KeyboardFrame!");
            }
            KeyboardFrame keyboardFrame = (KeyboardFrame) getRootView().findViewById(keyboardResId);
            keyboardFrame.setInputConnection(keyboardConnection);
            registerKeyboard(keyboardFrame);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.keyboardId = keyboardResId;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        keyboardResId = ss.keyboardId;
    }

    public EmbeddableKeyboardEditText(Context context) {
        super(context);
        init(null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmbeddableKeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public EmbeddableKeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public EmbeddableKeyboardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    public void registerKeyboard(@NonNull EmbeddableKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public InputConnection getInputConnection() {
        return keyboardConnection;
    }

    public interface InputConnection {
        void onKeyPressed(char symbol);

        void onBackspacePressed();
    }

    private static class SavedState extends BaseSavedState {
        int keyboardId;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            keyboardId = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(keyboardId);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
