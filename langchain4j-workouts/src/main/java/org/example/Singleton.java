package org.example;

public class Singleton {
    private static Singleton singletonInstance = null;

    // Private constructor to prevent others from instantiating
    private Singleton() {
        // Private constructor code here
    }

    // Public static synchronized method to get the singleton instance
    public static synchronized Singleton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Singleton();
        }
        return singletonInstance;
    }
}
