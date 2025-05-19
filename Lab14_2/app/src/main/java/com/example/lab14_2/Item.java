package com.example.lab14_2;

public class Item {
    private final String maso;
    private final String tieude;
    private Integer thich;

    public Item(String maso, String tieude, Integer thich) {
        this.maso = maso;
        this.tieude = tieude;
        this.thich = thich;
    }

    public String getTieude() {
        return tieude;
    }

    public String getMaso() {
        return maso;
    }

    public Integer getThich() {
        return thich;
    }

    public void setThich(Integer thich) {
        this.thich = thich;
    }
}