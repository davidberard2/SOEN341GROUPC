package com.projectfirebase.soen341.root;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import Fragments.ProfileFragment;

import static android.support.test.InstrumentationRegistry.registerInstance;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ProfileInstrumentedTest {
    private static Context context;

    private static ProfileFragment profileFragment;

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String NAME = "Test";

    private static FirebaseUser user;

    @BeforeClass
    public static void setup() {
        context = InstrumentationRegistry.getContext();

        final Intent intent = new Intent()
                .setClassName(context, "MainActivity")
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        profileFragment = new ProfileFragment();

        FirebaseAuth authRef = FirebaseAuth.getInstance();
        authRef.signInWithEmailAndPassword(EMAIL, PASSWORD);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Test
    public void temp() {
//        TextView name = (TextView) profileFragment.getView().findViewById(R.id.profile_name);
//        name.setText(NAME);
        onView(withId(R.id.profile_name)).perform(replaceText(NAME));
        onView(withId(R.id.profile_save_button)).perform(click());
        Assert.assertEquals(NAME, user.getDisplayName());
    }
}
