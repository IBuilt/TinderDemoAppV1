package com.demo.tinder.ui.favourite_user

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.tinder.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_favourite_users.*
import kotlinx.android.synthetic.main.layout_app_bar_short.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteUserActivity : AppCompatActivity() {

    private val favouriteUserViewModel: FavouriteUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_favourite_users)

        init()

        initUserAdapterSettings()
    }


    private fun init() {

        this.setSupportActionBar(toolbar)

        recyclerView.apply {

            this.adapter = favouriteUserViewModel.userAdapter

            this.layoutManager =
                    GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
        }
    }


    private fun initUserAdapterSettings() {

        lifecycleScope.launch {

            favouriteUserViewModel.tinderUsers.collectLatest {
                favouriteUserViewModel.userAdapter.submitData(it)
            }
        }
    }
}