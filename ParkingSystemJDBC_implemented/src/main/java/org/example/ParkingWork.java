package org.example;

import java.util.Scanner;

public class ParkingWork {
    static int parked;
    static Scanner scn = new Scanner(System.in);
    public static String carpark(){
        if(parked<App.getParkingLimit()){
            parked++;
        }
        else {
            return null;
        }
        System.out.println("Enter your Vehicle No. :");
        String vehicle = scn.next();
        return vehicle;
    }
    public static String vacant(){
        parked--;
        System.out.println("Enter your Vehicle No. :");
        String vehicle = scn.next();
        return vehicle;

    }
}
