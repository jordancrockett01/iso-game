package com.mygdx.game.model;

import com.mygdx.game.model.hud.Hud;

/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class SingletonsRepository {

    public static Hero hero;
    public static Hud hud;

    public static void init() {
        hero = new Hero();
        hud = new Hud();
    }
}
