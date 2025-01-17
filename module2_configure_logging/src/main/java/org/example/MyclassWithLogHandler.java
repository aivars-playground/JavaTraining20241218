package org.example;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyclassWithLogHandler {

    private static final Logger LOGGER = Logger.getLogger(MyclassWithLogHandler.class.getName());

    static {
        FileHandler fh = null;
        try {
            //Overwrites file on each run
            fh = new FileHandler("./logs/"+MyclassWithLogHandler.class.getName()+".log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.addHandler(fh);
    }

    public void logSomething() {
        LOGGER.log(Level.INFO, "writing to ./log");
        LOGGER.log(Level.INFO, "writing to again ./log");
    }
}
