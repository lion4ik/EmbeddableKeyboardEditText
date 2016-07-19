package ru.alexey.embeddablekeyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;

/**
 * Created by Alexey_Pushkarev1 on 07/13/2016.
 */
public class CalculatorKeyboard extends KeyboardFrame {
    public CalculatorKeyboard(Context context) {
        super(context);
    }

    public CalculatorKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalculatorKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CalculatorKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public @LayoutRes int getLayoutResource() {
        return R.layout.calculator_layout;
    }

    @Override
    public int getBackspaceResId() {
        return R.id.imbtnBackspace;
    }
}
