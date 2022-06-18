package com.skilltest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skilltest.dependency.DependencyUtils
import com.skilltest.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers

class EmployeeViewModelFactory : ViewModelProvider.Factory {
    lateinit var employeeRepository: EmployeeRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        employeeRepository = DependencyUtils.provideEmployeeRepository()
        if (modelClass.isAssignableFrom(ListUserViewModel::class.java)) {
            return ListUserViewModel(Dispatchers.Main,employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}