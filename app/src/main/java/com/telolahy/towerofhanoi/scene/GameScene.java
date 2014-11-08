package com.telolahy.towerofhanoi.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.telolahy.towerofhanoi.manager.SceneManager;
import com.telolahy.towerofhanoi.object.Ring;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.Stack;

/**
 * Created by stephanohuguestelolahy on 11/7/14.
 */
public class GameScene extends BaseScene {

    private Sprite mBackground;
    private Sprite mTower1, mTower2, mTower3;
    private Sprite mRings[];
    private Stack mStack1, mStack2, mStack3;
    private int mMoves;
    private int mOptimalMoves;
    private int mRingsCount;

    @Override
    public void createScene() {

        // add background
        mBackground = new Sprite(400, 240, mResourcesManager.gameBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(mBackground);

        // add the towers
        mTower1 = new Sprite(152, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower2 = new Sprite(400, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower3 = new Sprite(644, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        attachChild(mTower1);
        attachChild(mTower2);
        attachChild(mTower3);

        // initialize game
        mMoves = 0;
        mRingsCount = 3;
        mOptimalMoves = computeOptimalMoves(mRingsCount);
        mStack1 = new Stack();
        mStack2 = new Stack();
        mStack3 = new Stack();
        mRings = new Ring[mRingsCount];

        // add the rings
        for (int i = mRingsCount - 1; i >= 0; i--) {
            ITextureRegion ringTextureRegion = mResourcesManager.gameRingTextureRegions[i];
            Ring ring = new Ring(i, 0, 0, ringTextureRegion, mVertexBufferObjectManager) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                    if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
//                        return false;
//                    this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
//                    if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
//                        checkForCollisionsWithTowers(this);
//                    }
                    return true;
                }
            };

            mRings[i] = ring;

            attachChild(ring);

            // Add all rings to stack one
            putRingOnStack(ring, mStack1, mTower1);

            // Add touch handlers
            registerTouchArea(ring);
        }

        setTouchAreaBindingOnActionDownEnabled(true);
    }

    @Override
    public void onBackKeyPressed() {

        SceneManager.getInstance().loadMenuScene();
    }

    @Override
    public void disposeScene() {

        mBackground.detachSelf();
        mBackground.dispose();
        mTower1.detachSelf();
        mTower1.dispose();
        mTower2.detachSelf();
        mTower2.dispose();
        mTower3.detachSelf();
        mTower3.dispose();

        for (int i = 0; i < mRingsCount; i++) {
            mRings[i].detachSelf();
            mRings[i].dispose();
        }

        this.detachSelf();
        this.dispose();
    }


    // ----------------------------------------------------
    // Game Logic
    // ----------------------------------------------------

    private static int computeOptimalMoves(int ringsCount) {

        if (ringsCount == 1) {
            return 1;
        } else {
            return computeOptimalMoves(ringsCount - 1) * 2 + 1;
        }
    }

//    private void checkForCollisionsWithTowers(Ring ring) {
//
//        Stack stack = null;
//        Sprite tower = null;
//        if (ring.collidesWith(mTower1) && (mStack1.size() == 0 || ring.getmWeight() < ((Ring) mStack1.peek()).getmWeight())) {
//            stack = mStack1;
//            tower = mTower1;
//        } else if (ring.collidesWith(mTower2) && (mStack2.size() == 0 || ring.getmWeight() < ((Ring) mStack2.peek()).getmWeight())) {
//            stack = mStack2;
//            tower = mTower2;
//        } else if (ring.collidesWith(mTower3) && (mStack3.size() == 0 || ring.getmWeight() < ((Ring) mStack3.peek()).getmWeight())) {
//            stack = mStack3;
//            tower = mTower3;
//        } else {
//            stack = ring.getmStack();
//            tower = ring.getmTower();
//        }
//        putRingOnStack(ring, stack, tower);
//    }

    private void putRingOnStack(Ring ring, Stack stack, Sprite tower) {

        Stack currentStack = ring.getStack();
        if (currentStack != null) {
            ring.getStack().remove(ring);
        }
        if (stack != null && tower != null && stack.size() == 0) {
            float towerTop = tower.getY() - tower.getHeight() / 2;
            ring.setPosition(tower.getX(), towerTop + ring.getHeight() / 2);
        } else if (stack != null && tower != null && stack.size() > 0) {
            float ringTop = ((Ring) stack.peek()).getY() + ((Ring) stack.peek()).getHeight() / 2;
            ring.setPosition(tower.getX(), ringTop + ring.getHeight() / 2);
        }
        stack.add(ring);
        ring.setStack(stack);
        ring.setTower(tower);

        if (currentStack != null && stack != currentStack) {

            mMoves++;
            Log.i("hanoi", "Moves : " + mMoves + "/" + mOptimalMoves);
            checkGameOver();
        }
    }

    private void checkGameOver() {

        if (mStack3.size() == mRingsCount) {

            mActivity.runOnUiThread(new Runnable() {
                public void run() {

                    new AlertDialog.Builder(mActivity)
                            .setTitle("Congratulation !!!")
                            .setMessage("Level completed with " + mMoves + " moves.\nOptimal is " + mOptimalMoves)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
            });

        }
    }
}
