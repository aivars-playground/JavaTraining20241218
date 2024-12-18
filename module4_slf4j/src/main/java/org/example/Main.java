package org.example;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.logging.*;

public class Main {

   private static final Logger LOGGER_JUL = Logger.getLogger(Main.class.getName());

   private static final org.slf4j.Logger LOGGER_SLF4J = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        initializeJUL();
        LOGGER_JUL.log(Level.INFO, "Hello World (LOGGER_JUL)");
        LOGGER_SLF4J.info("Hello World (LOGGER_SLF4J)");
        switchToSLF4JBridge();
        LOGGER_JUL.log(Level.INFO, "Hello World (LOGGER_JUL -> SLF4JBridge) should not be in jul file");
    }

    static void initializeJUL() throws IOException {
        FileHandler fileHandler = new FileHandler("./logs/"+ Main.class.getName()+".log");
        LOGGER_JUL.addHandler(fileHandler);
    }

    static void switchToSLF4JBridge() {
        //redirecting all logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}