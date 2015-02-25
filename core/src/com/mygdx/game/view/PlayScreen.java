package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.IsoGame;
import com.mygdx.game.controller.GamePlayController;
import com.mygdx.game.controller.StaticStage;
import com.mygdx.game.model.Assets;
import com.mygdx.game.model.SingletonsRepository;

/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class PlayScreen extends MyScreen {

    private SpriteBatch batch;
    private IsometricStaggeredTiledMapRenderer renderer;

    Stage animatedStage;    // container for game actors
    StaticStage staticStage;    // container for game controls

    GamePlayController playController; // main game controller

    InputMultiplexer inputMultiplexer;  // merge inputs from stages (i like this lol)

    public PlayScreen(IsoGame game) {
        this.game = game;
        batch = new SpriteBatch();
        inputMultiplexer = new InputMultiplexer();

        renderer = new IsometricStaggeredTiledMapRenderer(Assets.tileMap);
        Gdx.input.setInputProcessor(inputMultiplexer);

        animatedStage = new Stage();
        staticStage = new StaticStage(inputMultiplexer, animatedStage.getCamera());

        // Animated Singletons
        SingletonsRepository.init();
        animatedStage.addActor(SingletonsRepository.hero);
//        movingStage.addActor(SingletonsRepository.fireOrb);
//        movingStage.addActor(SingletonsRepository.mana);
//        movingStage.addActor(SingletonsRepository.hp);
        staticStage.addActor(SingletonsRepository.hud);

        // Animated pools
//        PoolsReposetory.init();
//        for (Animation e : PoolsReposetory.enemyPool.getPool())
//            movingStage.addActor(e);
//        for (Animation s : PoolsReposetory.shotPool.getPool())
//            movingStage.addActor(s);
//        for (Animation b : PoolsReposetory.bloodPool.getPool())
//            movingStage.addActor(b);
//        for (Animation b : PoolsReposetory.sparksPool.getPool())
//            movingStage.addActor(b);
//        for (Animation l : PoolsReposetory.lightningPool.getPool())
//            movingStage.addActor(l);

        // Play controller
        playController = new GamePlayController(game, (OrthographicCamera)animatedStage.getCamera(), renderer);
        inputMultiplexer.addProcessor(new GestureDetector(20, 0.5f, 2, 0.15f, playController));

        // sound
        Assets.music.setLooping(true);
        Assets.music.setVolume(.5f);
        Assets.music.play();

    }
    @Override
    public void render(float delta) {

        // clear screen;
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // update camera position & projection
        animatedStage.getCamera().update();
        renderer.setView((OrthographicCamera) animatedStage.getCamera());
        renderer.render();

        batch.setProjectionMatrix(animatedStage.getCamera().combined);

        // update game state
        playController.update();
        // draw actors
        animatedStage.draw();
        // draw controllers
        staticStage.processInput();
        staticStage.draw();

//        if (Gdx.input.isTouched()) {
//            Ray camRay = animatedStage.getCamera().getPickRay(Gdx.input.getX(), Gdx.input.getY());
//            playController.getWorldToTilePos(camRay.origin.x, camRay.origin.y);
//        }

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
        staticStage.dispose();
        animatedStage.dispose();
        batch.dispose();
        Assets.tileMap.dispose();
        renderer.dispose();
    }

    @Override
    public void show() {
        super.show();
    }
}
