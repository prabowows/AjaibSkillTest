package com.skilltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.skilltest.model.UserModel
import com.skilltest.model.response.GithubUserResponse
import com.skilltest.network.EmployeeService
import com.skilltest.repository.EmployeeRepository
import com.skilltest.viewmodel.ListUserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ListUserViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    private lateinit var apiHelper: EmployeeService

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<LiveData<UserModel>>

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
        val employeeViewModel = ListUserViewModel(testDispatcher,employeeRepository)
        val response = Response.success(retroResponse)
        val channel = Channel<Response<GithubUserResponse>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(employeeRepository.fetchListUser("username")).thenReturn(flow )

       launch {
           channel.send(response)
       }
        employeeViewModel.fetchEmployeeList("username")
        Assert.assertEquals(1,employeeViewModel.employeeLiveData.value?.items?.size)
        Assert.assertEquals(false, employeeViewModel.fetchLoadStatus()?.value)
    }


/*
  val avatar: String, // https://reqres.in/img/faces/1-image.jpg
        val email: String, // george.bluth@reqres.in
        val first_name: String, // George
        val id: Int, // 1
        val last_name: String // Bluth
 */

}