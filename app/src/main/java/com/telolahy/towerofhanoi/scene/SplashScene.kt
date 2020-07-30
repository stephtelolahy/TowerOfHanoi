package com.telolahy.towerofhanoi.scene

import com.telolahy.towerofhanoi.Constants
import org.andengine.entity.sprite.Sprite

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
class SplashScene : BaseScene() {
  private var mBackground: Sprite? = null

  override fun createScene() {
    mBackground = Sprite(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2, mResourcesManager.splashTexture.textureRegion, mVertexBufferObjectManager)
    attachChild(mBackground)
  }

  override fun onBackKeyPressed() {}

  override fun disposeScene() {
    mBackground?.detachSelf()
    mBackground?.dispose()
    detachSelf()
    dispose()
  }
}