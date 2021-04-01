package com.demo.tinder

import android.content.Context
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.demo.tinder.data.repository.IUserRepository
import com.demo.tinder.data.source.local.FavouriteUserDao
import com.demo.tinder.db.AppDatabase
import com.demo.tinder.ui.favourite_user.FavouriteUserAdapter.Companion.USER_COMPARATOR
import com.demo.tinder.ui.favourite_user.FavouriteUserViewModel
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteUserViewModelTest {

    private lateinit var db: AppDatabase
    private lateinit var favouriteUserDao: FavouriteUserDao


    private val testDispatcher = TestCoroutineDispatcher()


    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    lateinit var userRepository: IUserRepository


    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.initMocks(this)

        setupDatabase()
    }


    private fun setupDatabase() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java
        ).build()

        favouriteUserDao = db.userDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        Dispatchers.resetMain()
    }


    @Test
    fun checkIsDataValid() {

        runBlocking(testDispatcher) {


            val usersInList = TestUser.findUser()


            db.userDao().insert(usersInList)


            val differ = AsyncPagingDataDiffer(
                    diffCallback = USER_COMPARATOR,
                    updateCallback = noopListUpdateCallback,
                    mainDispatcher = testDispatcher,
                    workerDispatcher = testDispatcher
            )


            doReturn(db.userDao().findAll()).whenever(userRepository).findAll()


            val favouriteUserViewModel = FavouriteUserViewModel(userRepository)


            val job = launch {
                favouriteUserViewModel.tinderUsers.collectLatest { pagingData ->
                    differ.submitData(pagingData)
                }
            }


            testDispatcher.advanceUntilIdle()


            assertThat(differ.snapshot()).containsExactly(
                    usersInList
            )


            job.cancel()
        }
    }


    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}