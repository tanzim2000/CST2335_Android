package algonquin.cst2335.saga0025;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * testing if the textview is changing to "You shall not pass!"
     */
    @Test
    public void testComplexityDisavowal() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.titleText));
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * testing if the textview is changing to "You shall not pass!"
     */
    @Test
    public void testComplexityConfirmation() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passwordField));
        appCompatEditText.perform(replaceText("passWORD123#$*"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.titleText));
        textView.check(matches(withText("Your password is complex enough")));
    }
    /**
     * testing if the app can recognise the absence of upper case
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view s
        ViewInteraction appCompatEditText = onView( withId(R.id.passwordField) );
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.titleText) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * testing if the app can recognise the absence of lower case
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view s
        ViewInteraction appCompatEditText = onView( withId(R.id.passwordField) );
        //type in password123#$*
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.titleText) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * testing if the app can recognise the absence of number
     */
    @Test
    public void testFindMissingNumber() {
        //find the view s
        ViewInteraction appCompatEditText = onView( withId(R.id.passwordField) );
        //type in password123#$*
        appCompatEditText.perform(replaceText("passWORD#%$"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.titleText) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }
    /**
     * testing if the app can recognise the absence of upper case
     */
    @Test
    public void testFindMissingSpecialChar() {
        //find the view s
        ViewInteraction appCompatEditText = onView( withId(R.id.passwordField) );
        //type in password123#$*
        appCompatEditText.perform(replaceText("passWORD123"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        //Click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView( withId(R.id.titleText) );
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
