package com.ecommerce.ecommerce.Utils;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {
    public static void main(String[] args) {
        String time = DateTimeUtil.obtenerFechaYHoraActual();
        System.out.println(time);
    }
}