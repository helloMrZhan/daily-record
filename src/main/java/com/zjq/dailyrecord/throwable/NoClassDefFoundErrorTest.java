package com.zjq.dailyrecord.throwable;

import org.junit.Test;

/**
 * @author zjq
 */
public class NoClassDefFoundErrorTest {
    public ClassWithInitErrors getClassWithInitErrors() {
        ClassWithInitErrors test = new ClassWithInitErrors();
        return test;
    }

    @Test(expected = NoClassDefFoundError.class)
    public void givenInitErrorInClass_whenloadClass_thenNoClassDefFoundError() {
        NoClassDefFoundErrorTest sample
                = new NoClassDefFoundErrorTest();
        sample.getClassWithInitErrors();
    }
}