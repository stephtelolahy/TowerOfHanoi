package com.telolahy.towerofhanoi.manager;

import com.telolahy.towerofhanoi.GameActivity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class ResourcesManager {

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vertexBufferObjectManager;

    public TextureRegion splashBackgroundTextureRegion;
    public TextureRegion menuBackgroundTextureRegion;

    private ITexture mSplashBackgroundTexture;
    private ITexture mMenuBackgroundTexture;

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vertexBufferObjectManager) {

        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public void loadSplashResources() {

        try {
            mSplashBackgroundTexture = new BitmapTexture(this.activity.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return activity.getAssets().open("gfx/splash/creative_games_logo.png");
                }
            });
            mSplashBackgroundTexture.load();
            splashBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(mSplashBackgroundTexture);

        } catch (IOException e) {
            Debug.e(e);
        }
    }

    public void unloadSplashResources() {

        mSplashBackgroundTexture.unload();
        splashBackgroundTextureRegion = null;
    }

    public void loadMenuResources() {

        try {
            mMenuBackgroundTexture = new BitmapTexture(this.activity.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return activity.getAssets().open("gfx/menu/menu_background.png");
                }
            });
            mMenuBackgroundTexture.load();
            menuBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(mMenuBackgroundTexture);

        } catch (IOException e) {
            Debug.e(e);
        }
    }

    public void unloadMenuResources() {

        mMenuBackgroundTexture.unload();
        menuBackgroundTextureRegion = null;
    }
}
