package com.telolahy.towerofhanoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

class Ring extends Sprite {
    private int mWeight;
    private Stack mStack; //this represents the stack that this ring belongs to
    private Sprite mTower;

    public Ring(int weight, float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mWeight = weight;
    }

    public int getmWeight() {
        return mWeight;
    }

    public Stack getmStack() {
        return mStack;
    }

    public void setmStack(Stack mStack) {
        this.mStack = mStack;
    }

    public Sprite getmTower() {
        return mTower;
    }

    public void setmTower(Sprite mTower) {
        this.mTower = mTower;
    }
}

public class MainActivity extends SimpleBaseGameActivity {

    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private static final int MAX_RING_COUNT = 4;

    private ITextureRegion mBackgroundTextureRegion, mTowerTextureRegion;
    private ITextureRegion mRingTextureRegions[] = new ITextureRegion[MAX_RING_COUNT];
    private Sprite mTower1, mTower2, mTower3;
    private Stack mStack1, mStack2, mStack3;
    private int mMoves;
    private int mOptimalMoves;
    private int mRingsCount;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {
        try {
            // 1 - Set up bitmap textures
            ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/background.png");
                }
            });
            ITexture towerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/tower.png");
                }
            });
            ITexture ring1 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring1.png");
                }
            });
            ITexture ring2 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring2.png");
                }
            });
            ITexture ring3 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring3.png");
                }
            });
            ITexture ring4 = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/ring4.png");
                }
            });

            // 2 - Load bitmap textures into VRAM
            backgroundTexture.load();
            towerTexture.load();
            ring1.load();
            ring2.load();
            ring3.load();
            ring4.load();

            // 3 - Set up texture regions
            mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
            mTowerTextureRegion = TextureRegionFactory.extractFromTexture(towerTexture);
            mRingTextureRegions[0] = TextureRegionFactory.extractFromTexture(ring1);
            mRingTextureRegions[1] = TextureRegionFactory.extractFromTexture(ring2);
            mRingTextureRegions[2] = TextureRegionFactory.extractFromTexture(ring3);
            mRingTextureRegions[3] = TextureRegionFactory.extractFromTexture(ring4);

            // 4 - Create the stacks
            mStack1 = new Stack();
            mStack2 = new Stack();
            mStack3 = new Stack();
        } catch (IOException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {

        // 7 - initialize moves count
        mMoves = 0;
        mRingsCount = 4;
        mOptimalMoves = MainActivity.hanoyFunction(mRingsCount);

        // 1 - Create new scene
        final Scene scene = new Scene();
        Sprite backgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(backgroundSprite);

        // 2 - Add the towers
        mTower1 = new Sprite(192, 80, mTowerTextureRegion, getVertexBufferObjectManager());
        mTower2 = new Sprite(400, 80, mTowerTextureRegion, getVertexBufferObjectManager());
        mTower3 = new Sprite(604, 80, mTowerTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(mTower1);
        scene.attachChild(mTower2);
        scene.attachChild(mTower3);

        // 3 - Create the rings
        for (int i = mRingsCount - 1; i >= 0; i--) {
            ITextureRegion ringTextureRegion = mRingTextureRegions[i + (MAX_RING_COUNT - mRingsCount)];
            Ring ring = new Ring(i, 0, 0, ringTextureRegion, getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
                        return false;
                    this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                    if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                        checkForCollisionsWithTowers(this);
                    }
                    return true;
                }
            };

            scene.attachChild(ring);

            // 4 - Add all rings to stack one
            // 5 - Initialize starting position for each ring
            putRingOnStack(ring, mStack1, mTower1);

            // 6 - Add touch handlers
            scene.registerTouchArea(ring);
        }

        scene.setTouchAreaBindingOnActionDownEnabled(true);

        return scene;
    }

    private void checkForCollisionsWithTowers(Ring ring) {

        Stack stack = null;
        Sprite tower = null;
        if (ring.collidesWith(mTower1) && (mStack1.size() == 0 || ring.getmWeight() < ((Ring) mStack1.peek()).getmWeight())) {
            stack = mStack1;
            tower = mTower1;
        } else if (ring.collidesWith(mTower2) && (mStack2.size() == 0 || ring.getmWeight() < ((Ring) mStack2.peek()).getmWeight())) {
            stack = mStack2;
            tower = mTower2;
        } else if (ring.collidesWith(mTower3) && (mStack3.size() == 0 || ring.getmWeight() < ((Ring) mStack3.peek()).getmWeight())) {
            stack = mStack3;
            tower = mTower3;
        } else {
            stack = ring.getmStack();
            tower = ring.getmTower();
        }
        putRingOnStack(ring, stack, tower);
    }

    private void putRingOnStack(Ring ring, Stack stack, Sprite tower) {

        Stack currentStack = ring.getmStack();
        if (currentStack != null) {
            ring.getmStack().remove(ring);
        }
        if (stack != null && tower != null && stack.size() == 0) {
            ring.setPosition(tower.getX() + tower.getWidth() / 2 - ring.getWidth() / 2, tower.getY() + tower.getHeight() - ring.getHeight());
        } else if (stack != null && tower != null && stack.size() > 0) {
            ring.setPosition(tower.getX() + tower.getWidth() / 2 - ring.getWidth() / 2, ((Ring) stack.peek()).getY() - ring.getHeight());
        }
        stack.add(ring);
        ring.setmStack(stack);
        ring.setmTower(tower);

        if (currentStack != null && stack != currentStack) {

            mMoves++;
            Log.i("hanoi", "Moves : " + mMoves + "/" + mOptimalMoves);
            checkGameOver();
        }
    }

    private void checkGameOver() {

        if (mStack3.size() == mRingsCount) {

            runOnUiThread(new Runnable() {
                public void run() {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Congratulation !!!")
                            .setMessage("Level completed with "+ mMoves + " moves.\nOptimal is " + mOptimalMoves + "\n Let's move to next challenge.")
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

    private static int hanoyFunction(int ringsCount) {
        if (ringsCount <= 1) {
            return 1;
        } else {
            return hanoyFunction(ringsCount - 1) * 2 + 1;
        }
    }
}