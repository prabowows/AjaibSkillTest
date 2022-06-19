package com.skilltest

import android.content.Intent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skilltest.activity.DetailUserActivity
import com.skilltest.model.UserRepoModel
import com.skilltest.model.response.UserDetailResponse
import com.skilltest.model.response.UserRepoResponse
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.*
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailUserActivityScenarioTest {

    @Test
    fun initActivity() {
        val scenario = launchActivity<DetailUserActivity>()
        scenario.onActivity { activity ->
            assertNotNull(activity.userRepoAdapter)
            assertNotNull(activity.detailUserViewModel)
        }
    }

    @Test
    fun setupAdapterCorrectly() {
        val scenario = launchActivity<DetailUserActivity>()
        scenario.onActivity { activity ->
            activity.userRepoRV.apply {
                assertNotNull(layoutManager)
                assertEquals(adapter, activity.userRepoAdapter)
            }
        }
    }

    @Test
    fun observerReturnCorrectly(){
        var fakeString = "fakeData"
        var fakeDetailUser = UserDetailResponse().apply {
            name = fakeString
            login = fakeString
            company = fakeString
            followers = 10
            location = fakeString
            email = fakeString
        }
        val scenario = launchActivity<DetailUserActivity>()
        scenario.onActivity { activity ->
            activity.detailUserViewModel.userDetailLiveData.value = fakeDetailUser

            assertEquals(activity.nameAccountTextView.text, fakeString)
            assertEquals(activity.accountNameTextView.text, fakeString)
            assertEquals(activity.accountAddressTextView.text, fakeString)
            assertEquals(activity.placeAccountTextview.text, fakeString)
            assertEquals(activity.emailAccountTextview.text, fakeString)
            assertEquals(activity.followerAccountTextview.text, "10")
        }
    }

    @Test
    fun observerListRepoReturnCorrectly(){
        var fakeList = UserRepoResponse()

        var fakeResponse : UserRepoResponse = fakeList
        val scenario = launchActivity<DetailUserActivity>()
        scenario.onActivity { activity ->
            activity.detailUserViewModel.userRepoLiveData.value = fakeResponse

            assertEquals(activity.userRepoAdapter?.itemCount, fakeResponse.size)
        }
    }

    @Test
    fun observerLoadingReturnCorrectly(){
        val scenario = launchActivity<DetailUserActivity>()
        scenario.onActivity { activity ->
            activity.detailUserViewModel.loading.value = true
            assertTrue(activity.loading_progressBar.isVisible)
        }
    }
}