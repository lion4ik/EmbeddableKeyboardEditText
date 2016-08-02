package com.keyboard.test.assertion;

import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Alexey_Pushkarev1 on 07/21/2016.
 */
public class CursorPositionMatcher extends TypeSafeMatcher<View> {

    private int startPosition;
    private int endPosition;

    public CursorPositionMatcher(int startPosition, int endPosition){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    protected boolean matchesSafely(View item) {
        if(item instanceof EditText) {
            EditText editText = (EditText)item;
            return editText.getSelectionStart() == startPosition && editText.getSelectionEnd() == endPosition;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("CursorPositionMatcher");
    }
}
