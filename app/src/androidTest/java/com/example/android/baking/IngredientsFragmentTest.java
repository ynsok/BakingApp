package com.example.android.baking;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredientsFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ingredientsFragmentTest() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.cardView_recycler),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView_MainActivity),
                                        1),
                                0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.TextView_DetailIngredients), withText("Recipe Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_container),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerView_Ingredients),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_ing), withText("Bittersweet chocolate (60-70% cacao)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView_Ingredients),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Bittersweet chocolate (60-70% cacao)")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_quantity), withText("350.0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView_Ingredients),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("350.0")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView_measure), withText("G"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerView_Ingredients),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("G")));

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
