package com.demo.tinder

import com.demo.lib.utils.DateUtils
import com.google.common.truth.Truth
import org.junit.Test

class CompareDateTest {

    @Test
    fun checkDateCompareIsValid() {

        val stringDate = DateUtils.getDate(777600000)

        Truth.assertThat(stringDate).isEqualTo("10-Jan-1970")
    }
}