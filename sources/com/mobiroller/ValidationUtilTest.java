package com.mobiroller;

import com.mobiroller.util.ValidationUtil;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

public class ValidationUtilTest {
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        TestCase.assertTrue(ValidationUtil.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_IncorrectEmailSimple_ReturnsFalse() {
        Assert.assertFalse(ValidationUtil.isValidEmail("nameemail.com"));
    }

    @Test
    public void emailValidator_IncorrectEmailSimple_ReturnsFalse_1() {
        Assert.assertFalse(ValidationUtil.isValidEmail("name@email"));
    }

    @Test
    public void emailValidator_IncorrectEmailNull_ReturnsFalse() {
        Assert.assertFalse(ValidationUtil.isValidEmail(null));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimple_ReturnTrue() {
        TestCase.assertTrue(ValidationUtil.isValidPassword("123456"));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimple_ReturnTrue_1() {
        TestCase.assertTrue(ValidationUtil.isValidPassword("1234567890ABC'1.."));
    }

    @Test
    public void passwordValidator_IncorrectPasswordLength_ReturnFalse() {
        Assert.assertFalse(ValidationUtil.isValidPassword("12345"));
    }

    @Test
    public void passwordValidator_IncorrectPasswordLength_ReturnFalse_1() {
        Assert.assertFalse(ValidationUtil.isValidPassword(""));
    }

    @Test
    public void passwordValidator_IncorrectPasswordNull_ReturnFalse() {
        Assert.assertFalse(ValidationUtil.isValidPassword(null));
    }
}
