package com.keyboard.test.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;

/**
 * Created by Alexey_Pushkarev1 on 07/18/2016.
 */
public class CursorPositionAction implements ViewAction {
    private final int startPosition;
    private final int endPosition;

    public CursorPositionAction(int startPosition, int endPosition) {
        if (startPosition > endPosition) {
            throw new IllegalArgumentException("startPosition can't be greater than endPosition");
        }
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isDisplayed();
    }

    @Override
    public String getDescription() {
        return "sets cursor to ";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((EditText) view).setSelection(startPosition, endPosition);
    }
}
