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
public class ReplaceSelectedTextAction implements ViewAction {
    private String replaceText;


    public ReplaceSelectedTextAction(String stringToBePlaced){
        replaceText = stringToBePlaced;
    }

    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isDisplayed();
    }

    @Override
    public String getDescription() {
        return "replace selected text";
    }

    @Override
    public void perform(UiController uiController, View view) {
        EditText editText = ((EditText) view);
        editText.getText().replace(editText.getSelectionStart(), editText.getSelectionEnd(), replaceText);
    }
}
