package com.telolahy.utils.resources

import android.graphics.Color
import org.andengine.opengl.font.Font
import org.andengine.opengl.font.FontFactory
import org.andengine.opengl.font.StrokeFont
import org.andengine.opengl.texture.TextureOptions
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas
import org.andengine.ui.activity.BaseGameActivity

/**
 * Created by stephanohuguestelolahy on 12/14/14.
 */
class FontDescription @JvmOverloads constructor(private val mFontFile: String, private val mFontSize: Int, private val mFontColor: Int, private val mBorderSize: Int = 0, private val mBorderColor: Int = Color.TRANSPARENT) {
  @JvmField
  var font: Font? = null
  fun load(gameActivity: BaseGameActivity) {
    val atlas = BitmapTextureAtlas(gameActivity.textureManager, 256, if (mFontSize > 50) 512 else 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA)
    font = FontFactory.createStrokeFromAsset(gameActivity.fontManager, atlas, gameActivity.assets, mFontFile, mFontSize.toFloat(), true, mFontColor, mBorderSize.toFloat(), mBorderColor)
    (font as StrokeFont?)?.load()
  }

}