package com.demo.tinder.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.demo.tinder.data.entities.User
import com.demo.tinder.data.repository.IUserRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel @ViewModelInject constructor(private val userRepository: IUserRepository) :
    ViewModel() {

    var userAdapter = UserAdapter()


    fun fetch() = userRepository.fetch()


    fun addLast(users: List<User>) {
        val old = userAdapter.getUsers()
        val new = mutableListOf<User>().apply {
            addAll(old)
            addAll(users)
        }
        val callback = UserDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        userAdapter.applyList(new)
        result.dispatchUpdatesTo(userAdapter)
    }


    fun removeFirst() {
        if (userAdapter.getUsers().isEmpty()) {
            return
        }

        val old = userAdapter.getUsers()
        val new = mutableListOf<User>().apply {
            addAll(old)
            removeAt(0)
        }

        val callback = UserDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        userAdapter.applyList(new)
        result.dispatchUpdatesTo(userAdapter)
    }


    fun saveFavouriteUserOnDatabase() {

        val user = userAdapter.getTopUser()

        user?.let {

            viewModelScope.launch {
                userRepository.saveFavourite(user)
            }
        }
    }


    fun isFetchedUsersFromServer() = userAdapter.isUserAvailableMoreThanOne().not()
}