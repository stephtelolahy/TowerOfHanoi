package com.telolahy.towerofhanoi.scene;

import android.util.Log;

import com.telolahy.towerofhanoi.manager.SceneManager;
import com.telolahy.towerofhanoi.object.LevelCompleteWindow;
import com.telolahy.towerofhanoi.object.Ring;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import java.util.Stack;

/**
 * Created by stephanohuguestelolahy on 11/7/14.
 */
public class GameScene extends BaseScene implements LevelCompleteWindow.LevelCompleteWindowListener {

    private Sprite mBackground;
    private Sprite mTower1, mTower2, mTower3;
    private Sprite mRings[];
    private Stack mStack1, mStack2, mStack3;
    private int mMoves;
    private int mOptimalMoves;
    private int mRingsCount;

    private HUD mGameHUD;
    private Text mMovesText;
    private Text mLevelText;
    private LevelCompleteWindow mLevelCompleteWindow;

    @Override
    public void createScene() {

        createHUD();
        createBackground();
        createPhysics();
        loadLevel(1);

        mLevelCompleteWindow = new LevelCompleteWindow(mVertexBufferObjectManager, this, this);
    }

    private void loadLevel(int level) {

        mMoves = 0;
        mRingsCount = level + 1;
        mOptimalMoves = computeOptimalMoves(mRingsCount);
        mStack1 = new Stack();
        mStack2 = new Stack();
        mStack3 = new Stack();
        mRings = new Ring[mRingsCount];

        mMovesText.setText("Moves: 0");
        mLevelText.setText("Level " + level);

        // add the rings
        for (int i = mRingsCount - 1; i >= 0; i--) {
            ITextureRegion ringTextureRegion = mResourcesManager.gameRingTextureRegions[i];
            Ring ring = new Ring(i, 0, 0, ringTextureRegion, mVertexBufferObjectManager) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (((Ring) this.getStack().peek()).getWeight() != this.getWeight())
                        return false;
                    this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                    if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                        checkForCollisionsWithTowers(this);
                    }
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

    private void createPhysics() {

        mTower1 = new Sprite(152, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower2 = new Sprite(400, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        mTower3 = new Sprite(644, 240, mResourcesManager.gameTowerTextureRegion, mVertexBufferObjectManager);
        attachChild(mTower1);
        attachChild(mTower2);
        attachChild(mTower3);
    }

    private void createBackground() {

        mBackground = new Sprite(400, 240, mResourcesManager.gameBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(mBackground);
    }

    private void createHUD() {

        mGameHUD = new HUD();

        mLevelText = new Text(100, 440, mResourcesManager.font, "Level 0123456789", new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager);
        mGameHUD.attachChild(mLevelText);

        mMovesText = new Text(400, 20, mResourcesManager.font, "Moves: 0123456789", new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager);
        mGameHUD.attachChild(mMovesText);

        mCamera.setHUD(mGameHUD);
    }

    private void addToMoves(int i) {
        mMoves += i;
        mMovesText.setText("Moves: " + mMoves);
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

        for (Sprite ring: mRings) {
            ring.detachSelf();
            ring.dispose();
        }

        this.detachSelf();
        this.dispose();

        mCamera.setHUD(null);
        mCamera.setChaseEntity(null);

        mLevelCompleteWindow.detachSelf();
        mLevelCompleteWindow.dispose();
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

    private void checkForCollisionsWithTowers(Ring ring) {

        Stack stack = null;
        Sprite tower = null;
        if (ring.collidesWith(mTower1) && (mStack1.size() == 0 || ring.getWeight() < ((Ring) mStack1.peek()).getWeight())) {
            stack = mStack1;
            tower = mTower1;
        } else if (ring.collidesWith(mTower2) && (mStack2.size() == 0 || ring.getWeight() < ((Ring) mStack2.peek()).getWeight())) {
            stack = mStack2;
            tower = mTower2;
        } else if (ring.collidesWith(mTower3) && (mStack3.size() == 0 || ring.getWeight() < ((Ring) mStack3.peek()).getWeight())) {
            stack = mStack3;
            tower = mTower3;
        } else {
            stack = ring.getStack();
            tower = ring.getTower();
        }
        putRingOnStack(ring, stack, tower);
    }

    private void putRingOnStack(Ring ring, Stack stack, Sprite tower) {

        Stack currentStack = ring.getStack();
        if (currentStack != null) {
            ring.getStack().remove(ring);
        }
        if (stack != null && tower != null && stack.size() == 0) {
            ring.setPosition(tower.getX(), tower.getY() - tower.getHeight() / 2 + ring.getHeight() / 2);
        } else if (stack != null && tower != null && stack.size() > 0) {
            ring.setPosition(tower.getX(), ((Ring) stack.peek()).getY() + ((Ring) stack.peek()).getHeight() / 2 + ring.getHeight() / 2);
        }
        stack.add(ring);
        ring.setStack(stack);
        ring.setTower(tower);

        if (currentStack != null && stack != currentStack) {

            addToMoves(1);
            checkGameOver();
        }
    }

    private void checkGameOver() {

        if (mStack3.size() == mRingsCount) {

            LevelCompleteWindow.StarsCount starsCount = mMoves == mOptimalMoves? LevelCompleteWindow.StarsCount.THREE : LevelCompleteWindow.StarsCount.TWO;
            mLevelCompleteWindow.display(starsCount, GameScene.this, mCamera);
        }
    }

    @Override
    public void levelCompleteWindowNextButtonClicked() {

        Log.i("", "move to next level");
    }

    @Override
    public void levelCompleteWindowReplayButtonClicked() {

        Log.i("", "reload game");
    }
}
