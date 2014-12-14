package com.telolahy.utils.resources;

import android.content.Context;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.debug.Debug;

import java.io.IOException;

/**
 * Created by stephanohuguestelolahy on 12/13/14.
 */
public class TextureDescription {

    public ITextureRegion textureRegion;

    private final String textureFile;
    private ITexture texture;

    public TextureDescription(String file) {
        textureFile = file;
    }

    public void load(TextureManager textureManager, Context context) {
        try {
            texture = new AssetBitmapTexture(textureManager, context.getAssets(), textureFile, TextureOptions.BILINEAR);
            textureRegion = TextureRegionFactory.extractFromTexture(texture);
            texture.load();
        } catch (IOException e) {
            texture = null;
            textureRegion = null;
            Debug.e(e);
        }
    }

    public void unload() {
        if (texture != null) {
            texture.unload();
        }
        textureRegion = null;
    }
}
