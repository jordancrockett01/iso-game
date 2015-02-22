package com.mygdx.game.controller;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.mygdx.game.model.Assets;
import com.mygdx.game.model.SingletonsRepository;


/**
 * Created by Zach Sogolow on 2/21/2015.
 */
public class StaticStage extends Stage implements InputProcessor {

    Camera camera;

    Touchpad touchpad;

    public StaticStage(InputMultiplexer multiplexer, Camera camera) {
        super();
        this.camera = camera;

        touchpad = new Touchpad(5, Assets.touchpadStyle);
        touchpad.setBounds(15, 15, 150, 150);
        addActor(touchpad);

        multiplexer.addProcessor(this);
    }

    float x, y;
    // hero & camera movement
    public void processInput() {
        if (touchpad.isTouched()
                && !SingletonsRepository.hero.isBehaviorLocked()) {
            if (!SingletonsRepository.hero.isRunning())
                SingletonsRepository.hero.run();

            // hero direction
            SingletonsRepository.hero.setDirection(Math.atan2(
                    touchpad.getKnobPercentY(), touchpad.getKnobPercentX()));

            // hero position
            x = SingletonsRepository.hero.getX()
                    + SingletonsRepository.hero.getSpeed()
                    * touchpad.getKnobPercentX();
            y = SingletonsRepository.hero.getY()
                    + SingletonsRepository.hero.getSpeed()
                    * touchpad.getKnobPercentY();
            if (x > 0
                    && x < Assets.PLAY_SCREEN_WIDTH
                    - SingletonsRepository.hero.getWidth() / 2)
                SingletonsRepository.hero.setX(x);
            if (y > 0
                    && y < Assets.PLAY_SCREEN_HEIGTH
                    - SingletonsRepository.hero.getHeight() / 2)
                SingletonsRepository.hero.setY(y);

            // hero position offset if camera stay static
            if (SingletonsRepository.hero.getX() > Assets.MOVING_CAM_MIN_X
                    && SingletonsRepository.hero.getX() < Assets.MOVING_CAM_MAX_X)
                SingletonsRepository.hero
                        .updateOffsetX(SingletonsRepository.hero.getSpeed()
                                * touchpad.getKnobPercentX());
            //  offsetY
            if (SingletonsRepository.hero.getY() > Assets.MOVING_CAM_MIN_Y
                    && SingletonsRepository.hero.getY() < Assets.MOVING_CAM_MAX_Y)
                SingletonsRepository.hero
                        .updateOffsetY(SingletonsRepository.hero.getSpeed()
                                * touchpad.getKnobPercentY());

            // camera movement
            x = SingletonsRepository.hero.getX();
            y = SingletonsRepository.hero.getY();
            if (x >= Assets.MOVING_CAM_MIN_X && x <= Assets.MOVING_CAM_MAX_X)
                camera.position.x = SingletonsRepository.hero.getX();
            if (y >= Assets.MOVING_CAM_MIN_Y && y <= Assets.MOVING_CAM_MAX_Y)
                camera.position.y = SingletonsRepository.hero.getY();

        } else if (!SingletonsRepository.hero.isStanding()
                && !SingletonsRepository.hero.isBehaviorLocked()) {
            SingletonsRepository.hero.stand();
        }
    }
}
