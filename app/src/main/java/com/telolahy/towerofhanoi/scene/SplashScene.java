package com.telolahy.towerofhanoi.scene;

import org.andengine.entity.sprite.Sprite;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SplashScene extends BaseScene {

    private Sprite mBackgroundSprite;

    @Override
    public void createScene() {

        mBackgroundSprite = new Sprite(400, 240, mResourcesManager.splashTextureRegion, mVertexBufferObjectManager);
//        backgroundSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//        IEntityModifier iem = new AlphaModifier(2000, 0, 255);
//        iem.setAutoUnregisterWhenFinished(true);
//        backgroundSprite.registerEntityModifier(iem);
        attachChild(mBackgroundSprite);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

        mBackgroundSprite.detachSelf();
        mBackgroundSprite.dispose();
        this.detachSelf();
        this.dispose();

    }
}
