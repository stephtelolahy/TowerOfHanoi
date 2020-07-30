package com.telolahy.towerofhanoi.scene

import com.telolahy.towerofhanoi.Constants
import com.telolahy.towerofhanoi.R
import org.andengine.entity.scene.background.Background
import org.andengine.entity.text.Text
import org.andengine.util.adt.color.Color

/**
 * Created by stephanohuguestelolahy on 11/5/14.
 */
class LoadingScene : BaseScene() {

  override fun createScene() {
    background = Background(Color.BLACK)
    val text = mActivity.resources.getString(R.string.loading)
    attachChild(Text(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2, mResourcesManager.mainFont.font, text, mVertexBufferObjectManager))
  }

  override fun onBackKeyPressed() {}
  override fun disposeScene() {}
}