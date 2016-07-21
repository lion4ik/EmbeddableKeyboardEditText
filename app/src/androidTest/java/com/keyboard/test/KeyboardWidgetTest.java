package com.keyboard.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.keyboard.test.action.CursorPositionAction;
import com.keyboard.test.action.DeleteSelectedTextAction;
import com.keyboard.test.action.ReplaceSelectedTextAction;
import com.keyboard.test.assertion.Matchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Alexey_Pushkarev1 on 07/15/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class KeyboardWidgetTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<KeyboardWidgetTutorialActivity> activityTestRule =
            new ActivityTestRule<>(KeyboardWidgetTutorialActivity.class);

    @Test
    public void testDeleteInTheMiddle(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("RS106135777EE"))
                .perform(new CursorPositionAction(2, 4))
                .perform(new DeleteSelectedTextAction(1))
                .check(matches(withText("RS6135777EE")))
                .check(matches(Matchers.withCursorPosition(2,2)));
    }

    @Test
    public void testReplaceInTheMiddle(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("RS106135777EE"))
                .perform(new CursorPositionAction(2, 4))
                .perform(new ReplaceSelectedTextAction("55"))
                .check(matches(withText("RS556135777EE")));
    }

    @Test
    public void testRestrictedCharactersAtBegining(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("QQRRR22"))
                .check(matches(withText("RRR22")));
    }

    @Test
    public void testSimpleInput(){
        onView(withId(R.id.target))
                .perform(typeText("RS106135777EE"))
                .check(matches(withText("RS106135777EE")));
    }

    @Test
    public void testRestrictedCharacters(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("dsgdfgdsgddfghdgf!^&$%^&#&!$"))
                .check(matches(withText("")));
    }

    @Test
    public void testDeleteAll(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("RS53453453dsfsdfsgdfs7SS"))
                .perform(new DeleteSelectedTextAction(50))
                .check(matches(withText("")));
    }

    @Test
    public void testDeleteEmpty(){
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(new DeleteSelectedTextAction(50))
                .check(matches(withText("")));
    }

    @Test
    public void testDeleteFromStartPosition() {
        onView(withId(R.id.target))
                .perform(clearText())
                .perform(typeText("RS106135777EE"))
                .perform(new CursorPositionAction(0, 0))
                .perform(new DeleteSelectedTextAction(50))
                .check(matches(withText("RS106135777EE")));
    }
}
