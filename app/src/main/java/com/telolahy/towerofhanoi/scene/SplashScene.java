package com.telolahy.towerofhanoi.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene() {

        Sprite backgroundSprite = new Sprite(0, 0, mResourcesManager.splashBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(backgroundSprite);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
