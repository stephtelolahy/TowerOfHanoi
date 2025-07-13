package com.telolahy.towerofhanoi.`object`

import com.telolahy.towerofhanoi.Constants
import com.telolahy.towerofhanoi.R
import com.telolahy.towerofhanoi.manager.GameManager
import com.telolahy.towerofhanoi.manager.ResourcesManager
import org.andengine.engine.camera.Camera
import org.andengine.entity.scene.Scene
import org.andengine.entity.sprite.Sprite
import org.andengine.entity.sprite.TiledSprite
import org.andengine.entity.text.Text
import org.andengine.input.touch.TouchEvent
import org.andengine.opengl.vbo.VertexBufferObjectManager

/**
 * Created by stephanohuguestelolahy on 11/8/14.
 */
class LevelCompleteWindow(pSpriteVertexBufferObject: VertexBufferObjectManager, scene: Scene, private val mListener: LevelCompleteWindowListener) : Sprite(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2, ResourcesManager.instance.gameCompleteWindowTexture.textureRegion, pSpriteVertexBufferObject) {

  interface LevelCompleteWindowListener {
    fun levelCompleteWindowNextButtonClicked()
    fun levelCompleteWindowReplayButtonClicked()
  }

  private var mStar1: TiledSprite? = null
  private var mStar2: TiledSprite? = null
  private var mStar3: TiledSprite? = null

  enum class StarsCount {
    ONE, TWO, THREE
  }

  private fun attachStars(pSpriteVertexBufferObject: VertexBufferObjectManager, scene: Scene) {
    val resourcesManager = ResourcesManager.instance
    var text = resourcesManager.activity.resources.getString(R.string.level_completed)
    val isOnLastLevel = GameManager.instance.isOnLastLevel
    if (isOnLastLevel) {
      text = resourcesManager.activity.resources.getString(R.string.last_level_completed)
    }
    attachChild(Text(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT - 100, resourcesManager.mainFont.font, text, resourcesManager.vertexBufferObjectManager))
    mStar1 = TiledSprite(275f, 260f, resourcesManager.gameCompleteStarsTexture.textureRegion, pSpriteVertexBufferObject)
    mStar2 = TiledSprite(400f, 260f, resourcesManager.gameCompleteStarsTexture.textureRegion, pSpriteVertexBufferObject)
    mStar3 = TiledSprite(525f, 260f, resourcesManager.gameCompleteStarsTexture.textureRegion, pSpriteVertexBufferObject)
    attachChild(mStar1)
    attachChild(mStar2)
    attachChild(mStar3)
    val retryButton: Sprite = object : Sprite(260f, 120f, resourcesManager.gameCompleteRetryTexture.textureRegion, pSpriteVertexBufferObject) {
      override fun onAreaTouched(pSceneTouchEvent: TouchEvent, pTouchAreaLocalX: Float, pTouchAreaLocalY: Float): Boolean {
        mListener.levelCompleteWindowReplayButtonClicked()
        return true
      }
    }
    attachChild(retryButton)
    scene.registerTouchArea(retryButton)
    if (isOnLastLevel) {
      retryButton.setPosition(400f, retryButton.y)
      return
    }
    val nextButton: Sprite = object : Sprite(540f, 120f, resourcesManager.gameCompleteNextTexture.textureRegion, pSpriteVertexBufferObject) {
      override fun onAreaTouched(pSceneTouchEvent: TouchEvent, pTouchAreaLocalX: Float, pTouchAreaLocalY: Float): Boolean {
        mListener.levelCompleteWindowNextButtonClicked()
        return true
      }
    }
    attachChild(nextButton)
    scene.registerTouchArea(nextButton)
  }

  /**
   * Change star`s tile index, depends on stars count.
   *
   * @param starsCount
   */
  fun display(starsCount: StarsCount?, scene: Scene, camera: Camera) {
    // Change stars tile index, based on stars count (1-3)
    when (starsCount) {
      StarsCount.ONE -> {
        mStar1?.currentTileIndex = 0
        mStar2?.currentTileIndex = 1
        mStar3?.currentTileIndex = 1
      }
      StarsCount.TWO -> {
        mStar1?.currentTileIndex = 0
        mStar2?.currentTileIndex = 0
        mStar3?.currentTileIndex = 1
      }
      StarsCount.THREE -> {
        mStar1?.currentTileIndex = 0
        mStar2?.currentTileIndex = 0
        mStar3?.currentTileIndex = 0
      }

        null -> TODO()
    }


    // Hide HUD
    camera.hud.isVisible = false

    // Disable camera chase entity
    camera.setChaseEntity(null)

    // Attach our level complete panel in the middle of camera
    setPosition(camera.centerX, camera.centerY)
    scene.attachChild(this)
  }

  init {
    attachStars(pSpriteVertexBufferObject, scene)
  }
}