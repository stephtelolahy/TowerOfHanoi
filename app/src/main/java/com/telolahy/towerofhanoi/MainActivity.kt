package com.telolahy.towerofhanoi

import android.view.KeyEvent
import com.telolahy.towerofhanoi.manager.ResourcesManager
import com.telolahy.towerofhanoi.manager.SceneManager
import org.andengine.engine.Engine
import org.andengine.engine.LimitedFPSEngine
import org.andengine.engine.camera.BoundCamera
import org.andengine.engine.handler.timer.ITimerCallback
import org.andengine.engine.handler.timer.TimerHandler
import org.andengine.engine.options.EngineOptions
import org.andengine.engine.options.ScreenOrientation
import org.andengine.engine.options.WakeLockOptions
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy
import org.andengine.entity.scene.Scene
import org.andengine.ui.IGameInterface.*
import org.andengine.ui.activity.BaseGameActivity
import java.io.IOException
import kotlin.system.exitProcess

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
class MainActivity : BaseGameActivity() {
  private lateinit var mCamera: BoundCamera

  override fun onCreateEngine(pEngineOptions: EngineOptions): Engine {
    return LimitedFPSEngine(pEngineOptions, 60)
  }

  override fun onCreateEngineOptions(): EngineOptions {
    mCamera = BoundCamera(0f, 0f, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT)
    val engineOptions = EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, FillResolutionPolicy(), mCamera)
    engineOptions.audioOptions.setNeedsMusic(true).setNeedsSound(true)
    engineOptions.wakeLockOptions = WakeLockOptions.SCREEN_ON
    return engineOptions
  }

  @Throws(IOException::class)
  override fun onCreateResources(pOnCreateResourcesCallback: OnCreateResourcesCallback) {
    ResourcesManager.instance.init(mEngine, this, mCamera, vertexBufferObjectManager)
    pOnCreateResourcesCallback.onCreateResourcesFinished()
  }

  override fun onCreateScene(pOnCreateSceneCallback: OnCreateSceneCallback) {
    SceneManager.instance.createSplashScene(pOnCreateSceneCallback)
  }

  override fun onPopulateScene(pScene: Scene, pOnPopulateSceneCallback: OnPopulateSceneCallback) {
    mEngine.registerUpdateHandler(TimerHandler(2f, ITimerCallback { pTimerHandler ->
      mEngine.unregisterUpdateHandler(pTimerHandler)
      SceneManager.instance.createMenuScene()
    }))
    pOnPopulateSceneCallback.onPopulateSceneFinished()
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      SceneManager.instance.currentScene?.onBackKeyPressed()
    }
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    exitProcess(0)
  }
}