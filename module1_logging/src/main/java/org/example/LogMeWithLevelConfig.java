package org.example;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogMeWithLevelConfig {

    private static final Logger LOGGER = Logger.getLogger(LogMeWithLevelConfig.class.getName());

    {
        //both logger and handler has filters!!!
        LOGGER.setLevel(Level.CONFIG);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.CONFIG);
        LOGGER.addHandler(consoleHandler);
    }

    void myMethod() {
        LOGGER.info("Logging myMethod");
        LOGGER.log(Level.WARNING, "Logging myMethod as warning");
    }

    static void myStaticMethod() {
        LOGGER.info("Static Logging myStaticMethod");
    }

    public void below700LevelsNotLoggedByConfig() {
        LOGGER.log(Level.CONFIG, "now visible CONFIG - lvl 700");
        LOGGER.log(Level.FINE, "not visible FINE - lvl 800");
    }
}
