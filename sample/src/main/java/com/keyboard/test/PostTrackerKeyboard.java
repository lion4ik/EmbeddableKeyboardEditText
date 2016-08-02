package com.keyboard.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.github.lion4ik.KeyboardFrame;

/**
 * Created by Alexey_Pushkarev1 on 07/19/2016.
 */
public class PostTrackerKeyboard extends KeyboardFrame {
    public PostTrackerKeyboard(Context context) {
        super(context);
    }

    public PostTrackerKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PostTrackerKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PostTrackerKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.post_tracker_keyboard;
    }

    @Override
    public int getBackspaceResId() {
        return R.id.imbtnBackspace;
    }
}
