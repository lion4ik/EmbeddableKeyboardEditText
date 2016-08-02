package com.keyboard.test.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import com.github.lion4ik.EmbeddableKeyboardEditText;

/**
 * Created by Alexey_Pushkarev1 on 07/19/2016.
 */
public class DeleteSelectedTextAction implements ViewAction {
    private int numberOfPressBackspace;

    public DeleteSelectedTextAction(int numberOfPressBackspace) {
        this.numberOfPressBackspace = numberOfPressBackspace;
    }

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
        EmbeddableKeyboardEditText editText = ((EmbeddableKeyboardEditText) view);
        for (int i = 0; i < numberOfPressBackspace; i++) {
            try {
                Thread.sleep(200);
                editText.getInputConnection().onBackspacePressed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
