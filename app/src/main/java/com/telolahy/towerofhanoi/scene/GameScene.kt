package com.telolahy.towerofhanoi.scene

import com.telolahy.towerofhanoi.`object`.LevelCompleteWindow
import com.telolahy.towerofhanoi.`object`.Ring
import com.telolahy.towerofhanoi.manager.GameManager
import com.telolahy.towerofhanoi.manager.SceneManager
import org.andengine.engine.camera.hud.HUD
import org.andengine.entity.sprite.Sprite
import org.andengine.entity.text.Text
import org.andengine.entity.text.TextOptions
import org.andengine.input.touch.TouchEvent
import org.andengine.util.adt.align.HorizontalAlign
import java.util.*

/**
 * Created by stephanohuguestelolahy on 11/7/14.
 */
class GameScene : BaseScene() {
  private var mBackground: Sprite? = null
  private var mTower1: Sprite? = null
  private var mTower2: Sprite? = null
  private var mTower3: Sprite? = null
  private lateinit var mRings: Array<Sprite?>
  private lateinit var mStack1: Stack<Any>
  private lateinit var mStack2: Stack<Any>
  private lateinit var mStack3: Stack<Any>
  private var mMoves = 0
  private var mOptimalMoves = 0
  private var mRingsCount = 0
  private var mGameHUD: HUD? = null
  private var mMovesText: Text? = null
  private var mLevelText: Text? = null
  private var mLevelCompleteWindow: LevelCompleteWindow? = null

  override fun createScene() {
    createHUD()
    createBackground()
    createPhysics()
    loadLevel(GameManager.instance.currentLevel())
    displayObjectiveMessage()
  }

  private fun displayObjectiveMessage() {
//        String message = mResourcesManager.activity.getResources().getString(R.string.objective_message) + " " + mOptimalMoves;
//        Text  text = new Text(400, 240, mResourcesManager.font, message, mVertexBufferObjectManager);
//        attachChild(text);
  }

  private fun loadLevel(level: Int) {
    mMoves = 0
    mRingsCount = level
    mOptimalMoves = computeOptimalMoves(mRingsCount)
    mStack1 = Stack()
    mStack2 = Stack()
    mStack3 = Stack()
    mRings = arrayOfNulls(mRingsCount)
    mLevelText?.text = "Level $level"
    updateMovesText()

    // add the rings
    for (i in mRingsCount - 1 downTo 0) {
      val ringTextureRegion = mResourcesManager.gameRingTextures[i].textureRegion
      val ring: Ring = object : Ring(i, 0f, 0f, ringTextureRegion, mVertexBufferObjectManager) {
        override fun onAreaTouched(pSceneTouchEvent: TouchEvent, pTouchAreaLocalX: Float, pTouchAreaLocalY: Float): Boolean {
          if ((this.stack?.peek() as Ring).weight != this.weight) return false
          this.setPosition(pSceneTouchEvent.x, pSceneTouchEvent.y)
          if (pSceneTouchEvent.action == TouchEvent.ACTION_UP) {
            checkForCollisionsWithTowers(this)
          }
          return true
        }
      }
      mRings[i] = ring
      attachChild(ring)

      // Add all rings to stack one
      mTower1?.let { putRingOnStack(ring, mStack1, it) }

      // Add touch handlers
      registerTouchArea(ring)
    }
    isTouchAreaBindingOnActionDownEnabled = true
  }

  private fun createPhysics() {
    mTower1 = Sprite(152f, 240f, mResourcesManager.gameTowerTexture.textureRegion, mVertexBufferObjectManager)
    mTower2 = Sprite(400f, 240f, mResourcesManager.gameTowerTexture.textureRegion, mVertexBufferObjectManager)
    mTower3 = Sprite(644f, 240f, mResourcesManager.gameTowerTexture.textureRegion, mVertexBufferObjectManager)
    attachChild(mTower1)
    attachChild(mTower2)
    attachChild(mTower3)
  }

  private fun createBackground() {
    mBackground = Sprite(400f, 240f, mResourcesManager.gameBackgroundTexture.textureRegion, mVertexBufferObjectManager)
    attachChild(mBackground)
  }

  private fun createHUD() {
    mGameHUD = HUD()
    mLevelText = Text(100f, 440f, mResourcesManager.mainFont.font, "Level 0123456789", TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager)
    mGameHUD?.attachChild(mLevelText)
    mMovesText = Text(400f, 20f, mResourcesManager.mainFont.font, "Moves: 0123456789", TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager)
    mGameHUD?.attachChild(mMovesText)
    val replayText: Text = object : Text(700f, 440f, mResourcesManager.mainFont.font, "Restart", TextOptions(HorizontalAlign.RIGHT), mVertexBufferObjectManager) {
      override fun onAreaTouched(pSceneTouchEvent: TouchEvent, pTouchAreaLocalX: Float, pTouchAreaLocalY: Float): Boolean {
        if (pSceneTouchEvent.action == TouchEvent.ACTION_UP) {
          SceneManager.instance.reloadGame()
        }
        return true
      }
    }
    registerTouchArea(replayText)
    mGameHUD?.attachChild(replayText)
    mCamera.hud = mGameHUD
  }

  private fun addToMoves(i: Int) {
    mMoves += i
    updateMovesText()
  }

  private fun updateMovesText() {
    mMovesText?.text = "Moves: $mMoves"
  }

  override fun onBackKeyPressed() {
    SceneManager.instance.loadMenuScene()
  }

  override fun disposeScene() {
    val engineLock = mActivity.engine.engineLock
    engineLock.lock()
    mBackground?.detachSelf()
    mBackground?.dispose()
    mTower1?.detachSelf()
    mTower1?.dispose()
    mTower2?.detachSelf()
    mTower2?.dispose()
    mTower3?.detachSelf()
    mTower3?.dispose()
    for (ring in mRings) {
      ring?.detachSelf()
      ring?.dispose()
    }
    mCamera.hud = null
    mCamera.setChaseEntity(null)
    mLevelCompleteWindow?.detachSelf()
    mLevelCompleteWindow?.dispose()
    detachSelf()
    dispose()
    engineLock.unlock()
  }

  private fun checkForCollisionsWithTowers(ring: Ring) {
    var stack: Stack<Any>? = null
    var tower: Sprite? = null
    if (ring.collidesWith(mTower1) && (mStack1.size == 0 || ring.weight < (mStack1.peek() as Ring).weight)) {
      stack = mStack1
      tower = mTower1
    } else if (ring.collidesWith(mTower2) && (mStack2.size == 0 || ring.weight < (mStack2.peek() as Ring).weight)) {
      stack = mStack2
      tower = mTower2
    } else if (ring.collidesWith(mTower3) && (mStack3.size == 0 || ring.weight < (mStack3.peek() as Ring).weight)) {
      stack = mStack3
      tower = mTower3
    } else {
      stack = ring.stack
      tower = ring.tower
    }

    if (stack != null && tower != null) {
      putRingOnStack(ring, stack, tower)
    }
  }

  private fun putRingOnStack(ring: Ring, stack: Stack<Any>, tower: Sprite) {
    val currentStack = ring.stack
    if (currentStack != null) {
      ring.stack?.remove(ring)
    }

    if (stack.size == 0) {
      ring.setPosition(tower.x, tower.y - tower.height / 2 + ring.height / 2)
    } else if (stack.size > 0) {
      ring.setPosition(tower.x, (stack.peek() as Ring).y + (stack.peek() as Ring).height / 2 + ring.height / 2)
    }
    stack.add(ring)
    ring.stack = stack
    ring.tower = tower
    if (currentStack != null && stack !== currentStack) {
      addToMoves(1)
      checkGameOver()
    }
  }

  private fun checkGameOver() {
    if (mStack3.size == mRingsCount) {
      mLevelCompleteWindow = LevelCompleteWindow(mVertexBufferObjectManager, this, object : LevelCompleteWindow.LevelCompleteWindowListener {
        override fun levelCompleteWindowNextButtonClicked() {
          SceneManager.instance.moveToNextGame()
        }

        override fun levelCompleteWindowReplayButtonClicked() {
          SceneManager.instance.reloadGame()
        }
      })
      val starsCount: LevelCompleteWindow.StarsCount = if (mMoves == mOptimalMoves) LevelCompleteWindow.StarsCount.THREE else LevelCompleteWindow.StarsCount.TWO
      mLevelCompleteWindow?.display(starsCount, this@GameScene, mCamera)
    }
  }

  companion object {
    // ----------------------------------------------------
    // Game Logic
    // ----------------------------------------------------
    private fun computeOptimalMoves(ringsCount: Int): Int {
      return if (ringsCount == 1) {
        1
      } else {
        computeOptimalMoves(ringsCount - 1) * 2 + 1
      }
    }
  }
}