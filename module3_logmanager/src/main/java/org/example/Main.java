package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        initLogManager();

        ClassWithLogManager classWithLogManager = new ClassWithLogManager();
        classWithLogManager.logSomeData();

    }

    public static void initLogManager() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}