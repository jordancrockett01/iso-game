package com.mygdx.game.model.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class Hud extends Table {

    Image map;

    public OrthographicCamera camera;

    public Hud() {
        super();

        camera = new OrthographicCamera();

        map = new Image();
        setFillParent(true);
        top().left();
        add(map).left().row();


    }

    public void update() {
        camera.update();

    }

}
