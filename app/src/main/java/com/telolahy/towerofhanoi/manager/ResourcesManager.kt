package com.telolahy.towerofhanoi.manager

import android.graphics.Color
import com.telolahy.towerofhanoi.MainActivity
import com.telolahy.utils.resources.FontDescription
import com.telolahy.utils.resources.TextureDescription
import com.telolahy.utils.resources.TiledTextureDescription
import org.andengine.engine.Engine
import org.andengine.engine.camera.BoundCamera
import org.andengine.opengl.vbo.VertexBufferObjectManager

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
class ResourcesManager {
  lateinit var engine: Engine
  lateinit var activity: MainActivity
  lateinit var camera: BoundCamera
  lateinit var vertexBufferObjectManager: VertexBufferObjectManager

  // common resources
  val mainFont = FontDescription("font/font.ttf", 50, Color.WHITE, 2, Color.BLACK)

  // splash resources
  val splashTexture = TextureDescription("gfx/splash/creative_games_logo.png")

  // menu resources
  val menuBackgroundTexture = TextureDescription("gfx/menu/menu_background.png")
  val menuPlayTexture = TextureDescription("gfx/menu/play.png")
  val menuHelpTexture = TextureDescription("gfx/menu/help.png")

  // game resources
  val gameBackgroundTexture = TextureDescription("gfx/game/game_background.png")
  val gameTowerTexture = TextureDescription("gfx/game/tower.png")
  val gameRingTextures = arrayOf(
      TextureDescription("gfx/game/ring0.png"),
      TextureDescription("gfx/game/ring1.png"),
      TextureDescription("gfx/game/ring2.png"),
      TextureDescription("gfx/game/ring3.png"),
      TextureDescription("gfx/game/ring4.png"),
      TextureDescription("gfx/game/ring5.png"),
      TextureDescription("gfx/game/ring6.png"),
      TextureDescription("gfx/game/ring7.png"),
      TextureDescription("gfx/game/ring8.png")
  )
  val gameCompleteWindowTexture = TextureDescription("gfx/game/game_window.png")
  val gameCompleteNextTexture = TextureDescription("gfx/game/next.png")
  val gameCompleteRetryTexture = TextureDescription("gfx/game/retry.png")
  val gameCompleteStarsTexture = TiledTextureDescription("gfx/game/star.png", 2, 1)

  fun init(engine: Engine, activity: MainActivity, camera: BoundCamera, vertexBufferObjectManager: VertexBufferObjectManager) {
    instance.engine = engine
    instance.activity = activity
    instance.camera = camera
    instance.vertexBufferObjectManager = vertexBufferObjectManager
  }

  fun loadSplashResources() {
    splashTexture.load(activity)
  }

  fun unloadSplashResources() {
    splashTexture.unload()
  }

  fun loadMenuResources() {
    loadMenuTextures()
    loadMenuFonts()
  }

  private fun loadMenuFonts() {
    mainFont.load(activity)
  }

  fun unloadMenuTextures() {
    menuBackgroundTexture.unload()
    menuPlayTexture.unload()
    menuHelpTexture.unload()
  }

  fun loadMenuTextures() {
    menuBackgroundTexture.load(activity)
    menuPlayTexture.load(activity)
    menuHelpTexture.load(activity)
  }

  fun loadGameResources() {
    loadGameTextures()
  }

  private fun loadGameTextures() {
    gameBackgroundTexture.load(activity)
    gameTowerTexture.load(activity)
    for (texture in gameRingTextures) {
      texture.load(activity)
    }
    gameCompleteWindowTexture.load(activity)
    gameCompleteStarsTexture.load(activity)
    gameCompleteNextTexture.load(activity)
    gameCompleteRetryTexture.load(activity)
  }

  fun unloadGameTextures() {
    gameBackgroundTexture.unload()
    gameTowerTexture.unload()
    for (texture in gameRingTextures) {
      texture.unload()
    }
    gameCompleteWindowTexture.unload()
    gameCompleteStarsTexture.unload()
  }

  companion object {
    val instance = ResourcesManager()
  }
}