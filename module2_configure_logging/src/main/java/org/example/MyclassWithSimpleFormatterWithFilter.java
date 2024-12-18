package org.example;

import java.io.IOException;
import java.util.logging.*;

public class MyclassWithSimpleFormatterWithFilter {

    private static final Logger LOGGER = Logger.getLogger(MyclassWithSimpleFormatterWithFilter.class.getName());

    static {
        FileHandler fh = null;
        try {
            //Overwrites file on each run
            fh = new FileHandler("./logs/"+ MyclassWithSimpleFormatterWithFilter.class.getName()+".log");
            fh.setLevel(Level.FINE);
            Formatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            Filter ft = s -> !s.getMessage().contains("secret");
            fh.setFilter(ft);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.addHandler(fh);
    }


    public void doNotLogSecret() {
        LOGGER.log(Level.INFO, "This is a secret message. should not go to a log file.");
        LOGGER.log(Level.INFO, "This is not a scrt message. totally safe to log.");
    }
}
