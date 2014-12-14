package com.telolahy.towerofhanoi.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SplashScene extends BaseScene {

    private Sprite mBackground;

    @Override
    public void createScene() {

        mBackground = new Sprite(400, 240, mResourcesManager.splashTextureRegion, mVertexBufferObjectManager);
        attachChild(mBackground);
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
