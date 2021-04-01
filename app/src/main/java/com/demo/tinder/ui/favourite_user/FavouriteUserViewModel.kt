package com.demo.tinder.ui.favourite_user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.demo.tinder.constants.Constant.Network.PAGE_SIZE
import com.demo.tinder.data.entities.User
import com.demo.tinder.data.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class FavouriteUserViewModel @ViewModelInject constructor(private val userRepository: IUserRepository) : ViewModel() {

    var userAdapter = FavouriteUserAdapter()


    val tinderUsers: Flow<PagingData<User>> =
            Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 2),
                    pagingSourceFactory = { userRepository.findAll() }
            ).flow.cachedIn(viewModelScope)
}