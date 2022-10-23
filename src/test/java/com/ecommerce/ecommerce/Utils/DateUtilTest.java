package com.ecommerce.ecommerce.Utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


class DateUtilTest {

    void year_is_leap_when_is_divisible_by_400() {
        assertThat(DateUtil.isLeapYear(1600), is(true));
        assertThat(DateUtil.isLeapYear(2000), is(true));
        assertThat(DateUtil.isLeapYear(2400), is(true));
    }

    @Test
    void year_is_not_leap_when_is_divisible_by_100_but_not_400() {
        assertThat(DateUtil.isLeapYear(1700), is(false));
        assertThat(DateUtil.isLeapYear(1800), is(false));
        assertThat(DateUtil.isLeapYear(1900), is(false));
    }

    @Test
    void year_is_leap_when_is_divisible_by_4_but_not_100() {
        assertThat(DateUtil.isLeapYear(1996), is(true));
        assertThat(DateUtil.isLeapYear(2004), is(true));
        assertThat(DateUtil.isLeapYear(2008), is(true));
    }
    @Test
    void year_is_not_leap_when_is_not_divisible_by_4() {
        assertThat(DateUtil.isLeapYear(2017), is(false));
        assertThat(DateUtil.isLeapYear(2019), is(false));
        assertThat(DateUtil.isLeapYear(2018), is(false));
    }
}