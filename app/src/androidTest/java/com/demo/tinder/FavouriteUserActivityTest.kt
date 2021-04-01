package com.demo.tinder

import android.content.Intent
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.tinder.ui.favourite_user.FavouriteUserActivity
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteUserActivityTest {

    @Test
    @UiThread
    fun checkIsShowingProperly() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), FavouriteUserActivity::class.java)
        ActivityScenario.launch<FavouriteUserActivity>(intent)


        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->

            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            Truth.assertThat(recyclerView.adapter).isNotNull()
        }
    }
}