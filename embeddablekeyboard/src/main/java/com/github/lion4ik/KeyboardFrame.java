package com.github.lion4ik;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.lion4ik.EmbeddableKeyboardEditText.InputConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey_Pushkarev1 on 07/12/2016.
 */
public abstract class KeyboardFrame extends FrameLayout implements EmbeddableKeyboard, BackspaceTimer.TimerAction {
    private static final int BACKSPACE_TIMER = 100;

    private InputConnection inputConnection;
    private View keyboardView;
    private BackspaceTimer timer;

    public KeyboardFrame(Context context) {
        super(context);
        init();
    }

    public KeyboardFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyboardFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public KeyboardFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public boolean isKeyboardShown() {
        return getVisibility() == View.VISIBLE;
    }

    @Override
    public void showKeyboard() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyboard() {
        setVisibility(View.GONE);
    }

    @Override
    public void onTick(long l){
        inputConnection.onBackspacePressed();
    }

    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public InputConnection getInputConnection() {
        return inputConnection;
    }

    public View getKeyboardView() {
        return keyboardView;
    }

    public abstract @LayoutRes int getLayoutResource();

    public abstract int getBackspaceResId();

    protected View initKeyboard() {
        ArrayList<View> childViewList = (ArrayList<View>) getAllChildren(getKeyboardView());

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                getInputConnection().onKeyPressed(btn.getText().charAt(0));
            }
        };

        View backSpaceView = null;
        for (int i = 0; i < childViewList.size(); i++) {
            View childView = childViewList.get(i);
            if (childView.getId() == getBackspaceResId()) {
                backSpaceView = childView;
            } else if(childView instanceof Button){
                childView.setOnClickListener(onClickListener);
            }
        }
        return backSpaceView;
    }

    private List<View> getAllChildren(View v) {

        ArrayList<View> result = new ArrayList<>();

        if (!(v instanceof ViewGroup)) {
            result.add(v);
            return result;
        }

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    private void init() {
        timer = new BackspaceTimer(BACKSPACE_TIMER, this);
        keyboardView = inflate(getContext(), getLayoutResource(), null);
        addView(keyboardView);
        View backspaceView = initKeyboard();
        initBackspaceView(backspaceView);
    }

    private void initBackspaceView(View backspaceView) {
        if (backspaceView != null) {
            backspaceView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            timer.start();
                            break;
                        case MotionEvent.ACTION_UP:
                            timer.cancel();
                            timer.onFinish();
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            timer.cancel();
                            timer.onFinish();
                            break;
                    }
                    return false;
                }
            });
        }
    }
}
