package com.store.streamsql.util;

public class Util {
    public static void line____________() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        String className = caller.getClassName();
        String methodName = caller.getMethodName();

        System.out.println(".   << -- >> " + methodName + " << -- >>             " + className);
//        System.out.println("Called from class: " + className);
//        System.out.println("Called from method: " + methodName);
    }
}
