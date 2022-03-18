package com.zjq.dailyrecord.throwable;

/**
 * @author zjq
 */
public class ClassNotFoundExceptionTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}