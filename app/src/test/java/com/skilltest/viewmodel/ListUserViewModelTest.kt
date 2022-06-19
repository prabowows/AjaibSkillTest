package com.skilltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skilltest.TestCoroutineRule
import com.skilltest.model.UserModel
import com.skilltest.model.response.GithubUserResponse
import com.skilltest.repository.EmployeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
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
class ListUserViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

    }
    private val testDispatcher = TestCoroutineDispatcher()
    @Test
    fun test_fetchEmployeeList() = testDispatcher.runBlockingTest {
        var data = UserModel(
            "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg",
            "george.bluth@reqres.in",
            "George",
            "https://s3.amazonaws.com/",
            "Bluth"
        )

        var list = arrayListOf<UserModel>()
        list.add(data)
        var retroResponse = GithubUserResponse(items = list)
        val employeeViewModel = ListUserViewModel(testDispatcher, employeeRepository)
        val response = Response.success(retroResponse)
        val channel = Channel<Response<GithubUserResponse>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(employeeRepository.fetchListUser("username")).thenReturn(flow )

       launch {
           channel.send(response)
       }
        employeeViewModel.fetchEmployeeList("username")
        Assert.assertEquals(1, employeeViewModel.employeeLiveData.value?.items?.size)
        Assert.assertEquals(false, employeeViewModel.fetchLoadStatus().value)
    }

}