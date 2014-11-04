package com.telolahy.towerofhanoi.manager;

import com.telolahy.towerofhanoi.scene.BaseScene;
import com.telolahy.towerofhanoi.scene.SplashScene;

import org.andengine.ui.IGameInterface;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public class SceneManager {

    private static final SceneManager INSTANCE = new SceneManager();

    private BaseScene mCurrentScene;
    private BaseScene mSplashScene;

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {

        ResourcesManager.getInstance().loadSplashScreen();
        mSplashScene = new SplashScene();
        mCurrentScene = mSplashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }
}
