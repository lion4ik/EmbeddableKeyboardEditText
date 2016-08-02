package com.keyboard.test.assertion;

import android.widget.EditText;

/**
 * Created by Alexey_Pushkarev1 on 07/21/2016.
 */
public class Matchers {

    public static CursorPositionMatcher withCursorPosition(int start, int end) {
        return new CursorPositionMatcher(start, end);
    }
}
