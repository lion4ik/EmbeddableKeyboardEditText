package com.keyboard.test.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;

/**
 * Created by Alexey_Pushkarev1 on 07/19/2016.
 */
public class DeleteSelectedTextAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isDisplayed();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(UiController uiController, View view) {
        EditText editText = ((EditText) view);
        editText.getText().delete(editText.getSelectionStart(), editText.getSelectionEnd());
    }
}
