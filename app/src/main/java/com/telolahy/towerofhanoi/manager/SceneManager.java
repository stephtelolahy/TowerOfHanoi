package com.telolahy.towerofhanoi.manager;

import com.telolahy.towerofhanoi.scene.BaseScene;
import com.telolahy.towerofhanoi.scene.MenuScene;
import com.telolahy.towerofhanoi.scene.SplashScene;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();

    private Engine mEngine = ResourcesManager.getInstance().engine;
    private BaseScene mCurrentScene;
    private BaseScene mSplashScene;
    private BaseScene mMenuScene;

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void setScene(BaseScene scene) {

        mEngine.setScene(scene);
        mCurrentScene = scene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {

        ResourcesManager.getInstance().loadSplashResources();
        mSplashScene = new SplashScene();
        mCurrentScene = mSplashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }

    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashResources();
        mSplashScene.disposeScene();
        mSplashScene = null;
    }

    public void createMenuScene() {

        ResourcesManager.getInstance().loadMenuResources();
        mMenuScene = new MenuScene();
//        loadingScene = new LoadingScene();
        setScene(mMenuScene);
        disposeSplashScene();
    }
}
