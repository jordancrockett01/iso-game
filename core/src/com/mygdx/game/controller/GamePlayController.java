package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
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
    private ShapeRenderer debugRenderer = new ShapeRenderer();
    private OrthographicCamera camera;

    private IsometricStaggeredTiledMapRenderer renderer;

    public GamePlayController(IsoGame game, OrthographicCamera camera, IsometricStaggeredTiledMapRenderer renderer) {

        this.game = game;
        this.camera = camera;
        this.renderer = renderer;
    }

    public void update() {

        // hero
        if (SingletonsRepository.hero.state == Animation.STATE.FREE) {
            Assets.music.stop();
            game.setScreen(new GameOverScreen(game));
        }

        SingletonsRepository.hero.animate();

        SingletonsRepository.hud.update();


    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("X: " + x + ", Y: " + y);

        Ray camRay = camera.getPickRay(x, y);
        getWorldToTilePos2(camRay.origin.x, camRay.origin.y);
        return false;
    }

    public void getWorldToTilePos2(float x, float y) {
        int regX = (int) x / 64;
        int regY = (int) (y / 32) * 2;

        int mmX = ((int) x % 64);
        int mmY = 32 - ((int) y % 32);

        Color color = new Color();
        color.set(Assets.hitTest.getPixel(mmX, mmY));

        int regDx = 0;
        int regDy = 0;

        String colorStr = color.toString();
        if (colorStr.equals("ffffffff")) {  // white OR black (hackery)
            regDx = 0;
            regDy = 0;
            System.out.println("WHITE");

        } else if (colorStr.equals("00ff00ff")) { // green
            regDx = 0;
            regDy = -1;
            System.out.println("GREEN");

        } else if (colorStr.equals("0000ffff")) { // blue
            regDx = 1;
            regDy = -1;
            System.out.println("BLUE");

        } else if (colorStr.equals("ffff00ff")) { // yellow
            regDx = 1;
            regDy = 1;
            System.out.println("YELLOW");

        } else if (colorStr.equals("ff0000ff")) { // red
            regDx = 0;
            regDy = 1;
            System.out.println("RED");

        }

        int tileX = regX + regDx;
        int tileY = regY + regDy;

        System.out.println(tileX + ", " + tileY);

        TiledMap map = Assets.tileMap;
        TiledMapTile debugTile = map.getTileSets().getTile(176);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        if(tileX >= 0 && tileY >= 0 && tileX < layer.getWidth() && tileY < layer.getHeight()){
            TiledMapTileLayer.Cell test = layer.getCell(tileX, tileY);
            test.setTile(debugTile);
        }


    }

    public void getWorldToTilePos(float x, float y) {
        //System.out.println(x + ", " + y);
        int TILE_WIDTH = 64;
        int TILE_HEIGHT = 32;
        boolean DEBUG_MODE =  true;
        int tileX = MathUtils.floor(((x) + TILE_WIDTH / 2) / TILE_WIDTH);
        int tileY = MathUtils.floor((2*(y) + TILE_HEIGHT / 2) / TILE_HEIGHT);
        System.out.println(tileX + "," + tileY);

        //In Debug Mode, draws a red dot where the cursor is on the screen.
        // A blue rectangle box should indicate which "tile" the cursor is
        // hovering over
        if (DEBUG_MODE) {
            debugRenderer.setProjectionMatrix(camera.combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
            debugRenderer.setColor(Color.RED);
            debugRenderer.circle(x, y, 5);
            debugRenderer.end();

            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            for(int debugX = 0; debugX < 20; debugX++){
                for (int debugY = 0; debugY < 20; debugY++) {
                    debugRenderer.setColor(Color.BLUE);
                    debugRenderer.rect(debugX * TILE_WIDTH, debugY
                            * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);

                    debugRenderer.setColor(Color.ORANGE);
                    debugRenderer.rect(debugX * TILE_WIDTH - (TILE_WIDTH / 2f), debugY
                            * TILE_HEIGHT + (TILE_HEIGHT / 2f), TILE_WIDTH, TILE_HEIGHT);
                }
            }
            debugRenderer.end();

            TiledMap map = Assets.tileMap;
            TiledMapTile debugTile = map.getTileSets().getTile(176);
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
            if(tileX >= 0 && tileY >= 0 && tileX < layer.getWidth() && tileY < layer.getHeight()){
                TiledMapTileLayer.Cell test = layer.getCell(tileX, tileY);
                test.setTile(debugTile);
            }
        }
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
