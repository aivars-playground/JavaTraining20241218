package org.example;

import java.io.IOException;
import java.util.logging.*;

public class MyclassWithSimpleFormatter {

    private static final Logger LOGGER = Logger.getLogger(MyclassWithSimpleFormatter.class.getName());

    static {
        FileHandler fh = null;
        try {
            //Overwrites file on each run
            fh = new FileHandler("./logs/"+ MyclassWithSimpleFormatter.class.getName()+".log");
            fh.setLevel(Level.FINE);
            Formatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
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
