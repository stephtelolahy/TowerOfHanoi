package com.telolahy.towerofhanoi.manager;

import android.graphics.Color;

import com.telolahy.towerofhanoi.GameActivity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class ResourcesManager {

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vertexBufferObjectManager;

    public Font font;

    public ITextureRegion splashTextureRegion;
    private BitmapTextureAtlas splashTextureAtlas;

    public ITextureRegion menuBackgroundTextureRegion;
    public ITextureRegion menuPlayTextureRegion;
    public ITextureRegion menuHelpTextureRegion;
    private BuildableBitmapTextureAtlas menuTextureAtlas;

    public ITextureRegion gameBackgroundTextureRegion;
    public ITextureRegion gameTowerTextureRegion;
    public ITextureRegion gameRingTextureRegions[] = new ITextureRegion[MAX_RING_COUNT];
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    public static final int MAX_RING_COUNT = 6;

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

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "creative_games_logo.png", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashResources() {

        splashTextureAtlas.unload();
        splashTextureRegion = null;
    }

    public void loadMenuResources() {

        loadMenuGraphics();
        loadMenuFonts();
    }

    private void loadMenuGraphics() {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
        menuPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
        menuHelpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "help.png");

        try {
            menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            menuTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    private void loadMenuFonts() {

        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    public void unloadMenuTextures() {
        menuTextureAtlas.unload();
    }

    public void loadMenuTextures() {
        menuTextureAtlas.load();
    }

    public void loadGameResources() {

        loadGameGraphics();
    }

    private void loadGameGraphics() {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        gameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_background.png");
        gameTowerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "tower.png");
        gameRingTextureRegions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring1.png");
        gameRingTextureRegions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring2.png");
        gameRingTextureRegions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring3.png");
        gameRingTextureRegions[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring4.png");
        gameRingTextureRegions[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring5.png");
        gameRingTextureRegions[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ring6.png");

        try {
            gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            gameTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    public void unloadGameTextures() {

        gameTextureAtlas.unload();
    }
}
