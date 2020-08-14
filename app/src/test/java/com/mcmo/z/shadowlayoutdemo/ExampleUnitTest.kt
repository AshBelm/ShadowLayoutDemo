package com.mcmo.z.shadowlayoutdemo

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.ceil

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var alpha = ceil(5 * 2.55f).toInt()
        println(alpha.toString(16)+" ，$alpha")
    }
}