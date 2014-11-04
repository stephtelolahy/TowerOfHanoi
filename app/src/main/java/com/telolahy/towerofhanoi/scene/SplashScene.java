package com.telolahy.towerofhanoi.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SplashScene extends BaseScene {

    private Sprite mBackground;

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

        mBackground.detachSelf();
        mBackground.dispose();
        this.detachSelf();
        this.dispose();

    }
}
