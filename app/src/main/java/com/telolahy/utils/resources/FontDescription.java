package com.telolahy.utils.resources;

import android.graphics.Color;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by stephanohuguestelolahy on 12/14/14.
 */
public class FontDescription {

    private final String mFontFile;
    private final int mFontSize;
    private final int mFontColor;
    private final int mBorderSize;
    private final int mBorderColor;

    public Font font;

    public FontDescription(String file, int fontSize, int fontColor, int borderSize, int borderColor) {
        mFontFile = file;
        mFontSize = fontSize;
        mFontColor = fontColor;
        mBorderSize = borderSize;
        mBorderColor = borderColor;
    }

    public FontDescription(String file, int fontSize, int fontColor) {
        this(file, fontSize, fontColor, 0, Color.TRANSPARENT);
    }

    public void load(BaseGameActivity gameActivity) {

        BitmapTextureAtlas atlas = new BitmapTextureAtlas(gameActivity.getTextureManager(), 256, mFontSize > 50 ? 512 : 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(gameActivity.getFontManager(), atlas, gameActivity.getAssets(), mFontFile, mFontSize, true, mFontColor, mBorderSize, mBorderColor);
        font.load();
    }
}
