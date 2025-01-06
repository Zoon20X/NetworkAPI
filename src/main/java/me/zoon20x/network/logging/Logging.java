package me.zoon20x.network.logging;

import me.zoon20x.network.Server.Server;

public class Logging {


    public static void log(Object o, Severity severity){
        switch (severity){
            case Debug:
                System.out.println(Color.CYAN);
                break;
            case Warning:
                System.out.println(Color.YELLOW_BRIGHT);
                break;
            case Critical:
                System.out.println(Color.RED_BRIGHT);
                break;
        }

        System.out.println(severity +" NetworkAPI>> " + o);
        System.out.println(Color.RESET);
    }

}
