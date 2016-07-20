package com.github.lion4ik;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Alexey_Pushkarev1 on 07/12/2016.
 */
public class EmbeddableKeyboardEditText extends EditText {
    private EmbeddableKeyboard keyboard;
    private InputConnection keyboardConnection;

    private void init(final AttributeSet attrs) {
        if(attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmbeddableKeyboardEditText);
            String allowedCharacters = a.getString(R.styleable.EmbeddableKeyboardEditText_availableSymbols);
            a.recycle();
            setFilters(new InputFilter[]{new CharactersFilter(allowedCharacters)});
        }

        setInputType(InputType.TYPE_NULL);
        setTextIsSelectable(true);
        setFocusableInTouchMode(true);

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setCursorVisible(true);
                return false;
            }
        });

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
                setSelection(selection);
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

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCursorVisible(true);
                if (keyboard != null) {
                    keyboard.showKeyboard();
                }
            }
        });
        // Disable standard keyboard hard way
        setOnTouchListener(new View.OnTouchListener() {
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
        });
        // Disable spell check
        setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
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
}
