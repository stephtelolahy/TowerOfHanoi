package com.telolahy.utils.resources

import org.andengine.opengl.texture.ITexture
import org.andengine.opengl.texture.TextureOptions
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture
import org.andengine.opengl.texture.region.ITiledTextureRegion
import org.andengine.opengl.texture.region.TextureRegionFactory
import org.andengine.ui.activity.BaseGameActivity
import org.andengine.util.debug.Debug
import java.io.IOException

/**
 * Created by stephanohuguestelolahy on 12/13/14.
 */
class TiledTextureDescription(private val textureFile: String, private val columns: Int, private val rows: Int) {
  @JvmField
  var textureRegion: ITiledTextureRegion? = null
  private var texture: ITexture? = null

  fun load(gameActivity: BaseGameActivity) {
    try {
      texture = AssetBitmapTexture(gameActivity.textureManager, gameActivity.assets, textureFile, TextureOptions.BILINEAR)
      textureRegion = TextureRegionFactory.extractTiledFromTexture(texture, columns, rows)
      (texture as AssetBitmapTexture).load()
    } catch (e: IOException) {
      texture = null
      textureRegion = null
      Debug.e(e)
    }
  }

  fun unload() {
    texture?.unload()
    textureRegion = null
  }

}