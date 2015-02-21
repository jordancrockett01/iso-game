package com.mygdx.game.controller;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.IsoGame;
import com.mygdx.game.model.Animation;
import com.mygdx.game.model.Assets;
import com.mygdx.game.model.SingletonsRepository;
import com.mygdx.game.view.GameOverScreen;

/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class GamePlayController implements GestureDetector.GestureListener {

    private IsoGame game;

    public GamePlayController(IsoGame game) {
        this.game = game;
    }

    public void update() {

        // hero
        if (SingletonsRepository.hero.state == Animation.STATE.FREE) {
            Assets.music.stop();
            game.setScreen(new GameOverScreen(game));
        }

        SingletonsRepository.hero.animate();

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
