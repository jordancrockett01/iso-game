package com.mygdx.game.model;

/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class SingletonsRepository {

    public static Hero hero;

    public static void init() {
        hero = new Hero();
    }
}
