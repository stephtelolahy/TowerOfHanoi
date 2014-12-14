package com.telolahy.towerofhanoi.manager;

import android.graphics.Color;

import com.telolahy.towerofhanoi.MainActivity;
import com.telolahy.towerofhanoi.texture.GameTexture;
import com.telolahy.utils.resources.FontDescription;
import com.telolahy.utils.resources.TextureDescription;
import com.telolahy.utils.resources.TiledTextureDescription;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
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
    public MainActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vertexBufferObjectManager;

    // common resources
    public final FontDescription mainFont = new FontDescription("font/font.ttf", 50, Color.WHITE, 2, Color.BLACK);

    // splash resources
    public final TextureDescription splashTexture = new TextureDescription("gfx/splash/creative_games_logo.png");

    // menu resources
    public final TextureDescription menuBackgroundTexture = new TextureDescription("gfx/menu/menu_background.png");
    public final TextureDescription menuPlayTexture = new TextureDescription("gfx/menu/play.png");
    public final TextureDescription menuHelpTexture = new TextureDescription("gfx/menu/help.png");

    // game resources
    public final TextureDescription gameReplayTexture = new TextureDescription("gfx/game/retry.png");
    public final TextureDescription gameBackgroundTexture = new TextureDescription("gfx/game/game_background.png");
    public final TextureDescription gameCompleteWindowTexture = new TextureDescription("gfx/game/game_window.png");
    public final TiledTextureDescription gameCompleteStarsTexture = new TiledTextureDescription("gfx/game/star.png", 2, 1);


    public ITextureRegion gameTowerTextureRegion;
    public ITextureRegion gameRingTextureRegions[] = new ITextureRegion[GameManager.LEVELS_COUNT];
    public ITextureRegion gameCompleteNextRegion;
    public ITextureRegion gameCompleteRetryRegion;
    private TexturePack texturePack;

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public static void prepareManager(Engine engine, MainActivity activity, BoundCamera camera, VertexBufferObjectManager vertexBufferObjectManager) {

        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public void loadSplashResources() {

        splashTexture.load(activity);
    }

    public void unloadSplashResources() {

        splashTexture.unload();
    }

    public void loadMenuResources() {

        loadMenuTextures();
        loadMenuFonts();
    }

    private void loadMenuFonts() {

        mainFont.load(activity);
    }

    public void unloadMenuTextures() {

        menuBackgroundTexture.unload();
        menuPlayTexture.unload();
        menuHelpTexture.unload();
    }

    public void loadMenuTextures() {

        menuBackgroundTexture.load(activity);
        menuPlayTexture.load(activity);
        menuHelpTexture.load(activity);
    }

    public void loadGameResources() {

        loadGameTextures();
    }

    private void loadGameTextures() {

        gameReplayTexture.load(activity);
        gameCompleteWindowTexture.load(activity);
        gameCompleteStarsTexture.load(activity);
        gameBackgroundTexture.load(activity);

        TexturePackTextureRegionLibrary texturePackLibrary = null;
        try {
            texturePack = new TexturePackLoader(engine.getTextureManager(), "gfx/game/").loadFromAsset(activity.getAssets(), "game_texture_pack.xml");
            texturePack.loadTexture();
            texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }
        gameTowerTextureRegion = texturePackLibrary.get(GameTexture.TOWER_ID);
        gameRingTextureRegions[0] = texturePackLibrary.get(GameTexture.RING1_ID);
        gameRingTextureRegions[1] = texturePackLibrary.get(GameTexture.RING2_ID);
        gameRingTextureRegions[2] = texturePackLibrary.get(GameTexture.RING3_ID);
        gameRingTextureRegions[3] = texturePackLibrary.get(GameTexture.RING4_ID);
        gameRingTextureRegions[4] = texturePackLibrary.get(GameTexture.RING5_ID);
        gameRingTextureRegions[5] = texturePackLibrary.get(GameTexture.RING6_ID);
        gameCompleteNextRegion = texturePackLibrary.get(GameTexture.NEXT_ID);
        gameCompleteRetryRegion = texturePackLibrary.get(GameTexture.RETRY_ID);
    }

    public void unloadGameTextures() {

        gameReplayTexture.unload();
        gameCompleteWindowTexture.unload();
        gameCompleteStarsTexture.unload();
        gameBackgroundTexture.unload();

        texturePack.unloadTexture();
    }
}
