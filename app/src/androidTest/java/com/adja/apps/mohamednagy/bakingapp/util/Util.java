package com.adja.apps.mohamednagy.bakingapp.util;

/**
 * Created by Mohamed Nagy on 4/11/2018 .
 * Project projects submission
 * Time    7:25 PM
 */

public class Util {
    private static final int FIT_SLEEPING = 500;

    public static void sleeping(){
        try {
            Thread.sleep(FIT_SLEEPING);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
