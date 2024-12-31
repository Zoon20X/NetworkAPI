package me.zoon20x.network.logging;

import me.zoon20x.network.Server.Server;

public class Logging {


    public static void log(Object o, Severity severity){
        System.out.println(severity +" NetworkAPI>> " + o);
    }

}
