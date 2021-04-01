package com.demo.tinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.rule.ActivityTestRule
import com.demo.lib.remote.FetchedResult
import com.demo.lib.utils.extensions.getOrAwaitValue
import com.demo.tinder.data.repository.IUserRepository
import com.demo.tinder.ui.user.UserActivity
import com.demo.tinder.ui.user.UserViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.io.IOException


@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {


    private val testDispatcher = TestCoroutineDispatcher()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    val mActivityRule = ActivityTestRule<UserActivity>(UserActivity::class.java, true, true)


    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    lateinit var userRepository: IUserRepository


    @Before
    fun setupDb() {

        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.initMocks(this)
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        Dispatchers.resetMain()
    }


    @Test
    fun checkIsFetchedSuccess() {

        runBlocking(testDispatcher) {


            val testingUsers = TestUser.findUsers()


            doReturn(MutableLiveData(FetchedResult.success(testingUsers))).whenever(userRepository).fetch(testDispatcher)


            val userViewModel = UserViewModel(userRepository)


            Truth.assertThat(userViewModel.fetch(testDispatcher).getOrAwaitValue().data).isEqualTo(testingUsers)
        }
    }


    @Test
    fun checkIsFetchedError() {

        runBlocking(testDispatcher) {


            doReturn(MutableLiveData(FetchedResult.error("Exception", null))).whenever(userRepository).fetch(testDispatcher)


            val userViewModel = UserViewModel(userRepository)


            Truth.assertThat(userViewModel.fetch(testDispatcher).getOrAwaitValue().message).isEqualTo("Exception")
        }
    }
}