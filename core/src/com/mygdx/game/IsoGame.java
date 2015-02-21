package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.model.Assets;
import com.mygdx.game.view.MenuScreen;

public class IsoGame extends Game {
    @Override
    public void create() {
        Assets.load();
        setScreen(new MenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
    }
}
