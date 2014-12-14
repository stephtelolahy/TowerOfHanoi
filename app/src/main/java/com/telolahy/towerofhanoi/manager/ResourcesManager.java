package com.telolahy.towerofhanoi.manager;

import android.graphics.Color;

import com.telolahy.towerofhanoi.MainActivity;
import com.telolahy.utils.resources.FontDescription;
import com.telolahy.utils.resources.TextureDescription;
import com.telolahy.utils.resources.TiledTextureDescription;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

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
    public final TextureDescription gameBackgroundTexture = new TextureDescription("gfx/game/game_background.png");
    public final TextureDescription gameTowerTexture = new TextureDescription("gfx/game/tower.png");
    public final TextureDescription gameRingTextures[] = {
            new TextureDescription("gfx/game/ring0.png"),
            new TextureDescription("gfx/game/ring1.png"),
            new TextureDescription("gfx/game/ring2.png"),
            new TextureDescription("gfx/game/ring3.png"),
            new TextureDescription("gfx/game/ring4.png"),
            new TextureDescription("gfx/game/ring5.png"),
            new TextureDescription("gfx/game/ring6.png"),
            new TextureDescription("gfx/game/ring7.png")
    };

    public final TextureDescription gameCompleteWindowTexture = new TextureDescription("gfx/game/game_window.png");
    public final TextureDescription gameCompleteNextTexture = new TextureDescription("gfx/game/next.png");
    public final TextureDescription gameCompleteRetryTexture = new TextureDescription("gfx/game/retry.png");
    public final TiledTextureDescription gameCompleteStarsTexture = new TiledTextureDescription("gfx/game/star.png", 2, 1);


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

        gameBackgroundTexture.load(activity);
        gameTowerTexture.load(activity);
        for (TextureDescription texture : gameRingTextures) {
            texture.load(activity);
        }

        gameCompleteWindowTexture.load(activity);
        gameCompleteStarsTexture.load(activity);
        gameCompleteNextTexture.load(activity);
        gameCompleteRetryTexture.load(activity);
    }

    public void unloadGameTextures() {

        gameBackgroundTexture.unload();
        gameTowerTexture.unload();
        for (TextureDescription texture : gameRingTextures) {
            texture.unload();
        }

        gameCompleteWindowTexture.unload();
        gameCompleteStarsTexture.unload();
    }
}
