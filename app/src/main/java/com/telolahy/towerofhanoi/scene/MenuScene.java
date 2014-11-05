package com.telolahy.towerofhanoi.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by stephanohuguestelolahy on 11/5/14.
 */
public class MenuScene extends BaseScene {

    private Sprite mBackground;

    @Override
    public void createScene() {

        Sprite backgroundSprite = new Sprite(400, 240, mResourcesManager.menuBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(backgroundSprite);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
