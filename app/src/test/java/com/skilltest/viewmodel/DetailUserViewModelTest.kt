package com.skilltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skilltest.TestCoroutineRule
import com.skilltest.model.response.UserDetailResponse
import com.skilltest.repository.EmployeeRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailUserViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun test_fetchEmployeeList() = testDispatcher.runBlockingTest {

        var fakeString = "fakeString"
        var retroResponse = UserDetailResponse().apply {
            name = fakeString
            login = fakeString
            company = fakeString
            followers = 10
            location = fakeString
            email = fakeString
        }
        val employeeViewModel = DetailUserViewModel(testDispatcher, employeeRepository)
        val response = Response.success(retroResponse)
        val channel = Channel<Response<UserDetailResponse>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(employeeRepository.fetchDetailUser("username")).thenReturn(flow)

        launch {
            channel.send(response)
        }
        employeeViewModel.fetchDetailUser("username")
        TestCase.assertEquals(employeeViewModel.userDetailLiveData.value?.name, fakeString)
        TestCase.assertEquals(employeeViewModel.userDetailLiveData.value?.login, fakeString)
        TestCase.assertEquals(employeeViewModel.userDetailLiveData.value?.company, fakeString)
        TestCase.assertEquals(employeeViewModel.userDetailLiveData.value?.email, fakeString)
        TestCase.assertEquals(employeeViewModel.userDetailLiveData.value?.location, fakeString)
        TestCase.assertEquals(
            employeeViewModel.userDetailLiveData.value?.followers.toString(),
            "10"
        )
        TestCase.assertEquals(false, employeeViewModel.fetchLoadStatus().value)
    }

}