package com.daphne.finalprojectcooking;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Food implements Serializable {

    String name;
    int id;
    Bitmap image;
    String tarif;
    String malzeme;

    public Food(String name, int id, Bitmap image, String malzeme, String tarif) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.malzeme = malzeme;
        this.tarif = tarif;
    }




}

