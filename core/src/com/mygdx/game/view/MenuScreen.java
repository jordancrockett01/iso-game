package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.IsoGame;
import com.mygdx.game.model.Assets;


/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class MenuScreen extends MyScreen {

    Stage stage;
    TextButton playButton;
    SpriteBatch batch;

    String welcomeText = "Hey! Thanks for playing. Press start.";

    public MenuScreen(IsoGame game) {
        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        float cellWidth = Gdx.graphics.getWidth() / 4;
        float cellHeight = Gdx.graphics.getHeight() / 4;

        Label label = new Label(welcomeText, Assets.defultSkin);
        label.setPosition(cellWidth / 3, 2.2f * cellHeight);
        stage.addActor(label);

        playButton = new TextButton("START", Assets.defultSkin);
        playButton.setPosition(cellWidth / 3, cellHeight);
        playButton.setSize(cellWidth, cellHeight);
        stage.addActor(playButton);
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(Assets.menuBg,
                (Math.max(0, Gdx.graphics.getWidth() - 700) / 2),
                (Math.max(0, Gdx.graphics.getHeight() - 480) / 2));
        batch.end();

        stage.getCamera().update();
        stage.draw();

        if (playButton.isChecked()) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
