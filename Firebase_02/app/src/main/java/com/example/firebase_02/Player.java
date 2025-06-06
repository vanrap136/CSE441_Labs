package com.example.firebase_02;

public class Player {
    public String member_code;
    public String username;
    public String avatar;
    public String birthday;
    public String hometown;
    public String residence;
    public double rating_single;
    public double rating_double;

    public Player() {}

    public Player(String member_code, String username, String avatar, String birthday,
                  String hometown, String residence, double rating_single, double rating_double) {
        this.member_code = member_code;
        this.username = username;
        this.avatar = avatar;
        this.birthday = birthday;
        this.hometown = hometown;
        this.residence = residence;
        this.rating_single = rating_single;
        this.rating_double = rating_double;
    }
}
