package com.telolahy.towerofhanoi.manager;

import com.telolahy.towerofhanoi.scene.BaseScene;
import com.telolahy.towerofhanoi.scene.GameScene;
import com.telolahy.towerofhanoi.scene.LoadingScene;
import com.telolahy.towerofhanoi.scene.MainMenuScene;
import com.telolahy.towerofhanoi.scene.SplashScene;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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
    private BaseScene mLoadingScene;
    private BaseScene mGameScene;

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    private void setScene(BaseScene scene) {

        mEngine.setScene(scene);
        mCurrentScene = scene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {

        ResourcesManager.getInstance().loadSplashResources();
        mSplashScene = new SplashScene();
        mCurrentScene = mSplashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }

    private void disposeSplashScene() {

        ResourcesManager.getInstance().unloadSplashResources();
        mSplashScene.disposeScene();
        mSplashScene = null;
    }

    public void createMenuScene() {

        ResourcesManager.getInstance().loadMenuResources();
        mMenuScene = new MainMenuScene();
        mLoadingScene = new LoadingScene();
        setScene(mMenuScene);
        disposeSplashScene();
    }

    public void loadGameScene() {

        setScene(mLoadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        mMenuScene = null;
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                mGameScene = new GameScene();
                setScene(mGameScene);
            }
        }));
    }

    public void loadMenuScene() {

        setScene(mLoadingScene);
        mGameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                mMenuScene = new MainMenuScene();
                setScene(mMenuScene);
            }
        }));
    }

    public BaseScene getCurrentScene() {

        return mCurrentScene;
    }

    public void reloadGame() {

        setScene(mLoadingScene);
        mGameScene.disposeScene();
        mEngine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                mGameScene = new GameScene();
                setScene(mGameScene);
            }
        }));
    }

    public void moveToNextGame() {

        int level = GameManager.getInstance().currentLevel();
        if (level < GameManager.LEVELS_COUNT) {
            GameManager.getInstance().saveCurrentLevel(level + 1);
        }
        reloadGame();
    }
}
