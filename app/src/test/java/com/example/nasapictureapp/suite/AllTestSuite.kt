package com.example.nasapictureapp.suite

import com.example.nasapictureapp.suite.viewmodel.AllViewModelSUite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    AllViewModelSUite::class,
)
class AllTestSuite