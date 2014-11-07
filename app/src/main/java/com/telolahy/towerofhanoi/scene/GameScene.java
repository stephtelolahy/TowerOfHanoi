package com.telolahy.towerofhanoi.scene;

import com.telolahy.towerofhanoi.manager.SceneManager;

import org.andengine.entity.sprite.Sprite;

import java.util.Stack;

/**
 * Created by stephanohuguestelolahy on 11/7/14.
 */
public class GameScene extends BaseScene {

    private Sprite mTower1, mTower2, mTower3;
    private Stack mStack1, mStack2, mStack3;
    private int mMoves;
    private int mOptimalMoves;
    private int mRingsCount;

    @Override
    public void createScene() {

        // create the stacks
        mStack1 = new Stack();
        mStack2 = new Stack();
        mStack3 = new Stack();

        // initialize moves count
        mMoves = 0;
        mRingsCount = 6;
        mOptimalMoves = computeOptimalMoves(mRingsCount);

        // add background
        Sprite backgroundSprite = new Sprite(400, 240, mResourcesManager.gameBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(backgroundSprite);

        // add the towers
        mTower1 = new Sprite(152, 238, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower2 = new Sprite(400, 238, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower3 = new Sprite(644, 238, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        attachChild(mTower1);
        attachChild(mTower2);
        attachChild(mTower3);

    }

    private static int computeOptimalMoves(int ringsCount) {

        if (ringsCount == 1) {
            return 1;
        } else {
            return computeOptimalMoves(ringsCount - 1) * 2 + 1;
        }
    }

    @Override
    public void onBackKeyPressed() {

        SceneManager.getInstance().loadMenuScene();
    }

    @Override
    public void disposeScene() {

    }
}
