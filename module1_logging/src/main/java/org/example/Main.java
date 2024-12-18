package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================System logger");
        //Java 10, by default uses jul as a backend
        LogMe_SL logMe_sl = new LogMe_SL();
        logMe_sl.myMethod();
        logMe_sl.myStaticMethod();


        Thread.sleep(100);
        System.out.println("========================Jul logger (java.util)");
        LogMe.myStaticMethod();
        LogMe logMe_jul = new LogMe();
        logMe_jul.myMethod();
        logMe_jul.overloads();                        //custom bundle
        logMe_jul.below800LevelsNotLoggedByDefault(); //does not show...

        Thread.sleep(100);
        System.out.println("========================Jul logger (java.util) with log level (700 Config)");
        LogMeWithLevelConfig logMeWithLevelConfig = new LogMeWithLevelConfig();
        logMeWithLevelConfig.below700LevelsNotLoggedByConfig();

    }
}