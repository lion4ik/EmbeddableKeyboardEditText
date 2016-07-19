package com.keyboard.test;

import android.support.test.rule.ActivityTestRule;

import com.keyboard.test.action.CursorPositionAction;
import com.keyboard.test.action.DeleteSelectedTextAction;
import com.keyboard.test.action.ReplaceSelectedTextAction;

import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Alexey_Pushkarev1 on 07/15/2016.
 */
public class KeyboardWidgetTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<KeyboardWidgetTutorialActivity> activityTestRule =
            new ActivityTestRule<>(KeyboardWidgetTutorialActivity.class);

    @Test
    public void validateEditText() {
        try {
            onView(withId(R.id.target))
                    .perform(typeText("RS106135777EE"))
                    .check(matches(withText("RS106135777EE")));
            Thread.sleep(1000);
            onView(withId(R.id.target))
                    .perform(clearText())
                    .perform(typeText("dsgdfgdsgd"))
                    .check(matches(withText("")));
            Thread.sleep(1000);
            onView(withId(R.id.target))
                    .perform(clearText())
                    .perform(typeText("QQRRR22"))
                    .check(matches(withText("RRR22")));
            Thread.sleep(1000);
            onView(withId(R.id.target))
                    .perform(clearText())
                    .perform(typeText("RS106135777EE"))
                    .perform(new CursorPositionAction(2, 4))
                    .perform(new ReplaceSelectedTextAction("55"))
                    .check(matches(withText("RS556135777EE")));
            Thread.sleep(1000);
            onView(withId(R.id.target))
                    .perform(clearText())
                    .perform(typeText("RS106135777EE"))
                    .perform(new CursorPositionAction(2, 4))
                    .perform(new DeleteSelectedTextAction(1))
                    .check(matches(withText("RS6135777EE")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
