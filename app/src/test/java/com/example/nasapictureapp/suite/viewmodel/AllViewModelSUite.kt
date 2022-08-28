package com.example.nasapictureapp.suite.viewmodel

import com.example.nasapictureapp.features.picture.suite.PictureViewModelSuite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    PictureViewModelSuite::class,
)
class AllViewModelSUite {
}