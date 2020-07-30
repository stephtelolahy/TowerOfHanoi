package com.telolahy.towerofhanoi.manager

import com.telolahy.towerofhanoi.Constants.LEVELS_COUNT
import com.telolahy.towerofhanoi.scene.*
import com.telolahy.utils.AppRater.checkAppLaunched
import org.andengine.engine.handler.timer.ITimerCallback
import org.andengine.engine.handler.timer.TimerHandler
import org.andengine.ui.IGameInterface.OnCreateSceneCallback

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
class SceneManager {
  var currentScene: BaseScene? = null
  private val mEngine = ResourcesManager.instance.engine
  private var mSplashScene: BaseScene? = null
  private var mMenuScene: BaseScene? = null
  private var mLoadingScene: BaseScene? = null
  private var mGameScene: BaseScene? = null

  private fun setScene(scene: BaseScene?) {
    mEngine.scene = scene
    currentScene = scene
  }

  fun createSplashScene(pOnCreateSceneCallback: OnCreateSceneCallback) {
    ResourcesManager.instance.loadSplashResources()
    mSplashScene = SplashScene()
    currentScene = mSplashScene
    pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene)
  }

  private fun disposeSplashScene() {
    ResourcesManager.instance.unloadSplashResources()
    mSplashScene?.disposeScene()
    mSplashScene = null
  }

  fun createMenuScene() {
    ResourcesManager.instance.loadMenuResources()
    mMenuScene = MainMenuScene()
    mLoadingScene = LoadingScene()
    setScene(mMenuScene)
    disposeSplashScene()
    UpgradeManager.instance.checkUpgrade()
    checkAppLaunched()
  }

  fun loadGameScene() {
    setScene(mLoadingScene)
    ResourcesManager.instance.unloadMenuTextures()
    mMenuScene = null
    mEngine.registerUpdateHandler(TimerHandler(0.1f, ITimerCallback { pTimerHandler ->
      mEngine.unregisterUpdateHandler(pTimerHandler)
      ResourcesManager.instance.loadGameResources()
      mGameScene = GameScene()
      setScene(mGameScene)
    }))
  }

  fun loadMenuScene() {
    setScene(mLoadingScene)
    mGameScene?.disposeScene()
    ResourcesManager.instance.unloadGameTextures()
    mEngine.registerUpdateHandler(TimerHandler(0.1f, ITimerCallback { pTimerHandler ->
      mEngine.unregisterUpdateHandler(pTimerHandler)
      ResourcesManager.instance.loadMenuTextures()
      mMenuScene = MainMenuScene()
      setScene(mMenuScene)
    }))
  }

  fun reloadGame() {
    setScene(mLoadingScene)
    mGameScene?.disposeScene()
    mEngine.registerUpdateHandler(TimerHandler(0.2f, ITimerCallback { pTimerHandler ->
      mEngine.unregisterUpdateHandler(pTimerHandler)
      mGameScene = GameScene()
      setScene(mGameScene)
    }))
  }

  fun moveToNextGame() {
    val level = GameManager.instance.currentLevel()
    if (level < LEVELS_COUNT) {
      GameManager.instance.saveCurrentLevel(level + 1)
    }
    reloadGame()
  }

  companion object {
    val instance = SceneManager()
  }
}