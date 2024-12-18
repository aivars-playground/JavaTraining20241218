package org.example;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogMe {

    private static final Logger LOGGER = Logger.getLogger(LogMe.class.getName());

    void myMethod() {
        LOGGER.info("Logging myMethod");
        LOGGER.log(Level.WARNING, "Logging myMethod as warning");
    }

    static void myStaticMethod() {
        LOGGER.info("Static Logging myStaticMethod");
    }

    public void below800LevelsNotLoggedByDefault() {
        LOGGER.log(Level.CONFIG, "not visible CONFIG - lvl 700");
        LOGGER.log(Level.FINE, "not visible FINE - lvl 800");
    }

    public void overloads() {
        LOGGER.logp(Level.INFO,"someclassname", "somemethod", "my message");
        LOGGER.logrb(Level.WARNING, ResourceBundle.getBundle("custom_messages", Locale.getDefault()),"illegal_argument");
        LOGGER.logrb(Level.WARNING, ResourceBundle.getBundle("custom_messages", new Locale("en","GB")),"illegal_argument");
    }
}
