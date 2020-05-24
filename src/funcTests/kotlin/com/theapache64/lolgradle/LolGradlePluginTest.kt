package com.theapache64.lolgradle


import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.junit.Before
import org.junit.Rule

/**
 * Created by theapache64 : May 24 Sun,2020 @ 12:51
 */
class LolGradlePluginTest {
    @get:Rule
    val testProjectDir = TemporaryFolder()

    @Before
    fun setUp(){

    }
}