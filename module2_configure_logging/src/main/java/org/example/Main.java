package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyclassWithLogHandler myclassWithLogHandler = new MyclassWithLogHandler();
        myclassWithLogHandler.logSomething();    //writes xml

        Thread.sleep(100);
        MyclassWithSimpleFormatter myclassWithSimpleFormatter = new MyclassWithSimpleFormatter();
        myclassWithSimpleFormatter.logSomething(); //writes string as (default console appender / handler)

        Thread.sleep(100);
        MyclassWithSimpleFormatterWithFilter myclassWithSimpleFormatterWithFilter = new MyclassWithSimpleFormatterWithFilter();
        myclassWithSimpleFormatterWithFilter.doNotLogSecret();


    }
}