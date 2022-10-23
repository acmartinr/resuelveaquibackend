package com.ecommerce.ecommerce.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {
    @Test
    public void weak_when_has_less_than_8_letters() {
        assertEquals(PasswordUtil.SecurityLevel.WEAK, PasswordUtil.assesPassword("1!dd567"));
    }

    @Test
    public void weak_when_has_only_letters() {
        assertEquals(PasswordUtil.SecurityLevel.WEAK, PasswordUtil.assesPassword("zsfsaffefefeefefffeeeffxx"));
    }
    @Test
    public void medium_when_has_letters_and_numbers() {
        assertEquals(PasswordUtil.SecurityLevel.MEDIUM, PasswordUtil.assesPassword("zsfsafeeeffxx1333"));
    }

    @Test
    public void strong_when_has_letters_numbers_and_symbols() {
        assertEquals(PasswordUtil.SecurityLevel.STRONG, PasswordUtil.assesPassword("zsfsafeeeffxx1333**"));
    }
}