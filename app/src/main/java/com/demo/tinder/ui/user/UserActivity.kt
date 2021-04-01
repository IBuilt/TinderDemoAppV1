package com.demo.tinder.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import com.demo.lib.remote.FetchedResult
import com.demo.lib.utils.extensions.isInt
import com.demo.tinder.R
import com.demo.tinder.data.entities.User
import com.demo.tinder.ui.favourite_user.FavouriteUserActivity
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_user.*
import kotlinx.android.synthetic.main.layout_app_bar_short.*

@AndroidEntryPoint
class UserActivity : AppCompatActivity(), CardStackListener {

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var cardStackLayoutManager: CardStackLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_user)

        initUi()

        fetchAndObserverUser()

        handleEvents()
    }



    private fun initUi() {

        this.setSupportActionBar(toolbar)


        initLayoutManagerSettings()


       cardStackTinder.apply {

            this.adapter = userViewModel.userAdapter

            this.layoutManager = cardStackLayoutManager

            this.itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }
    }


    private fun initLayoutManagerSettings() {

        cardStackLayoutManager = CardStackLayoutManager(this, this)

        cardStackLayoutManager.setStackFrom(StackFrom.None)
        cardStackLayoutManager.setVisibleCount(3)
        cardStackLayoutManager.setTranslationInterval(8.0f)
        cardStackLayoutManager.setScaleInterval(0.95f)
        cardStackLayoutManager.setSwipeThreshold(0.3f)
        cardStackLayoutManager.setMaxDegree(20.0f)
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL)
        cardStackLayoutManager.setCanScrollHorizontal(true)
        cardStackLayoutManager.setCanScrollVertical(true)
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        cardStackLayoutManager.setOverlayInterpolator(LinearInterpolator())
    }


    private fun handleEvents() {

        fabRefresh.setOnClickListener {
            fetchAndObserverUser()
        }


        fabShowFavouriteUsers.setOnClickListener {
            showFavouriteUsersActivity()
        }
    }


    private fun showFavouriteUsersActivity() {

        Intent(this, FavouriteUserActivity::class.java).apply {
            startActivity(this)
        }
    }


    private fun fetchAndUpdateUserToUi() {

        if (userViewModel.isFetchedUsersFromServer())
            fetchAndObserverUser()
    }


    private fun fetchAndObserverUser() {

        userViewModel.fetch()
            .observe(this) { fetchedResult ->

                when (fetchedResult.status) {

                    FetchedResult.Status.LOADING -> {
                        progressBar.show()
                        fabRefresh.hide()
                    }

                    FetchedResult.Status.SUCCESS -> {
                        progressBar.hide()
                        doAfterOnFetchedSuccess(fetchedResult.data!!)
                    }

                    FetchedResult.Status.ERROR -> {
                        progressBar.hide()
                        doAfterOnFetchedError(fetchedResult.message)
                        fabRefresh.show()
                    }
                }
            }
    }


    private fun doAfterOnFetchedSuccess(tinderUsers: List<User>) {
        userViewModel.addLast(tinderUsers)
    }


    private fun doAfterOnFetchedError(errorMessage: String?) {
        errorMessage?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }


    private fun doOnMoveToLeftCardSwiped() {

        userViewModel.removeFirst()

        fetchAndUpdateUserToUi()
    }


    private fun doOnMoveToRightCardSwiped() {

        userViewModel.saveFavouriteUserOnDatabase()

        userViewModel.removeFirst()

        fetchAndUpdateUserToUi()
    }


    override fun onCardSwiped(direction: Direction?) {

        direction.let { nsDirection ->

            when (nsDirection) {

                Direction.Left -> {
                    doOnMoveToLeftCardSwiped()
                }

                Direction.Right -> {
                    doOnMoveToRightCardSwiped()
                }

                else -> {
                    // Skipped
                }
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }
}