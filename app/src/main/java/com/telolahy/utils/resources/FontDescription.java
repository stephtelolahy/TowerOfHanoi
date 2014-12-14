package com.telolahy.utils.resources;

import android.content.Context;
import android.graphics.Color;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

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

    public void load(FontManager fontManager, TextureManager textureManager, Context context) {

        BitmapTextureAtlas atlas = new BitmapTextureAtlas(textureManager, 256, mFontSize > 50 ? 512 : 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(fontManager, atlas, context.getAssets(), mFontFile, mFontSize, true, mFontColor, mBorderSize, mBorderColor);
        font.load();
    }
}
