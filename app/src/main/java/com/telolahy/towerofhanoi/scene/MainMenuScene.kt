package com.telolahy.towerofhanoi.scene

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import com.telolahy.towerofhanoi.Constants
import com.telolahy.towerofhanoi.R
import com.telolahy.towerofhanoi.manager.SceneManager
import org.andengine.entity.scene.menu.MenuScene
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener
import org.andengine.entity.scene.menu.item.IMenuItem
import org.andengine.entity.scene.menu.item.SpriteMenuItem
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator
import org.andengine.entity.sprite.Sprite
import kotlin.system.exitProcess

/**
 * Created by stephanohuguestelolahy on 11/5/14.
 */
class MainMenuScene : BaseScene() {
  private var mMenuChildScene: MenuScene? = null
  private var mBackground: Sprite? = null

  override fun createScene() {
    createBackground()
    createMenuChildScene()
  }

  private fun createBackground() {
    mBackground = Sprite(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT / 2, mResourcesManager.menuBackgroundTexture.textureRegion, mVertexBufferObjectManager)
    attachChild(mBackground)
  }

  private fun createMenuChildScene() {
    mMenuChildScene = MenuScene(mCamera)
    mMenuChildScene?.setPosition(-140f, -200f)
    val playMenuItem: IMenuItem = ScaleMenuItemDecorator(SpriteMenuItem(MENU_PLAY, mResourcesManager.menuPlayTexture.textureRegion, mVertexBufferObjectManager), 1.2f, 1f)
    val helpMenuItem: IMenuItem = ScaleMenuItemDecorator(SpriteMenuItem(MENU_HELP, mResourcesManager.menuHelpTexture.textureRegion, mVertexBufferObjectManager), 1.2f, 1f)
    mMenuChildScene?.addMenuItem(playMenuItem)
    mMenuChildScene?.addMenuItem(helpMenuItem)
    mMenuChildScene?.buildAnimations()
    mMenuChildScene?.isBackgroundEnabled = false
    helpMenuItem.setPosition(helpMenuItem.x + 260, playMenuItem.y)
    mMenuChildScene?.onMenuItemClickListener = IOnMenuItemClickListener { pMenuScene, pMenuItem, pMenuItemLocalX, pMenuItemLocalY ->
      when (pMenuItem.id) {
        MENU_PLAY -> {
          SceneManager.instance.loadGameScene()
          true
        }
        MENU_HELP -> {
          displayHelpDialog()
          true
        }
        else -> false
      }
    }
    childScene = mMenuChildScene
  }

  override fun onBackKeyPressed() {
    exitProcess(0)
  }

  override fun disposeScene() {
    mBackground?.detachSelf()
    mBackground?.dispose()
    detachSelf()
    dispose()
  }

  private fun displayHelpDialog() {
    mActivity.runOnUiThread {
      val textView = TextView(mActivity)
      val padding = mActivity.resources.getDimensionPixelSize(R.dimen.dialog_padding)
      textView.setPadding(padding, padding, padding, padding)
      textView.setTextAppearance(mActivity, R.style.dialog_text)
      val text = mActivity.resources.getString(R.string.app_help)
      val message = SpannableString(text)
      Linkify.addLinks(message, Linkify.WEB_URLS)
      textView.text = message
      textView.movementMethod = LinkMovementMethod.getInstance()
      var title = ""
      try {
        val pInfo = mActivity.packageManager.getPackageInfo(mActivity.packageName, 0)
        val versionName = pInfo.versionName
        title = mActivity.resources.getString(R.string.app_name) + " v" + versionName
      } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
      }
      val positiveText = mActivity.resources.getString(R.string.ok)
      AlertDialog.Builder(mActivity)
          .setTitle(title)
          .setCancelable(true)
          .setIcon(android.R.drawable.ic_dialog_info)
          .setPositiveButton(positiveText, null)
          .setView(textView)
          .create()
          .show()
    }
  }

  companion object {
    private const val MENU_PLAY = 0
    private const val MENU_HELP = 1
  }
}