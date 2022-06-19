package com.skilltest

import androidx.core.view.isVisible
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skilltest.activity.MainActivity
import com.skilltest.model.UserModel
import com.skilltest.model.response.GithubUserResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import kotlinx.android.synthetic.main.activity_main.loading_progressBar

@RunWith(AndroidJUnit4::class)
class MainActivityScenarioTest {

    @Test
    fun initActivity() {

        val scenario = launchActivity<MainActivity>()
        scenario.onActivity { activity ->
            assertNotNull(activity.userAdapter)
            assertNotNull(activity.listUserViewModel)
            assertFalse(activity.loading_progressBar.isVisible)
        }
    }

    @Test
    fun validateSearchQueryModeReturnCorrectly(){

        val scenario = launchActivity<MainActivity>()
        scenario.onActivity { activity ->
            activity.searchCV.setQuery("Ya", false);
            assert(activity.userAdapter?.itemCount==0)
        }
    }

    @Test
    fun observerLoadingReturnCorrectly(){
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity { activity ->
            activity.listUserViewModel.loading.value = true
            assertTrue(activity.loading_progressBar.isVisible)
        }
    }

    @Test
    fun observerListUserReturnCorrectly(){
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity { activity ->
            var fakeList = arrayListOf(UserModel())
            var githubUserResponse = GithubUserResponse().apply {
                totalCount = 1
                items = fakeList
            }
            activity.listUserViewModel.employeeLiveData.value = githubUserResponse
            assertEquals(activity.userAdapter?.itemCount, fakeList.size)
        }
    }
}