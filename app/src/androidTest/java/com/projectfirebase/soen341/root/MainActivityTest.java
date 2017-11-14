package com.projectfirebase.soen341.root;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    FirebaseAuth authRef = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<MainActivity> MainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void clickAllMenuOptions() throws Exception {
    // Home
        onView(withId(R.id.action_home))
                .perform(click());
        onView(withId(R.id.fragment_home))
                    .check(matches(isDisplayed()));

    // Search
        onView(withId(R.id.action_search))
                .perform(click());
        onView(withId(R.id.fragment_search))
                .check(matches(isDisplayed()));

    // Search
        onView(withId(R.id.action_addition))
                .perform(click());
        onView(withId(R.id.fragment_add_listing_item))
                .check(matches(isDisplayed()));

    // Profile
        onView(withId(R.id.action_profile))
                .perform(click());
            if (authRef.getCurrentUser() != null) {
                onView(withId(R.id.fragment_profile))
                        .check(matches(isDisplayed()));
            }
            else {
                onView(withId(R.id.fragment_profile_login))
                        .check(matches(isDisplayed()));
            }

    // Favorite
        onView(withId(R.id.action_favorite))
                .perform(click());
        onView(withId(R.id.fragment_favorite))
                .check(matches(isDisplayed()));
    }
}
