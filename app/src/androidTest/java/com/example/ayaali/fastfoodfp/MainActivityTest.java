package com.example.ayaali.fastfoodfp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.ayaali.fastfoodfp.activity.RecipesListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.core.deps.guava.base.Predicates.instanceOf;
import static org.hamcrest.CoreMatchers.is;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<RecipesListActivity> mActivityRule = new ActivityTestRule<>(
            RecipesListActivity.class);

    @Test
    public void GridViewTest()
    {
        onData(
                is(instanceOf(String.class))
        );
    }

}
