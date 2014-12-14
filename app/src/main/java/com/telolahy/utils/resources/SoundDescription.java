package com.telolahy.utils.resources;

import android.content.Context;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.util.debug.Debug;

import java.io.IOException;

/**
 * Created by stephanohuguestelolahy on 12/13/14.
 */
public class SoundDescription {

    public interface SoundConditioner {

        boolean enableSound();
    }

    private final String mSoundFile;
    private final SoundConditioner mSoundConditioner;
    private Sound mSound;


    public SoundDescription(String file, SoundConditioner conditioner) {
        mSoundFile = file;
        mSoundConditioner = conditioner;
    }

    public void load(SoundManager manager, Context context) {

        try {
            mSound = SoundFactory.createSoundFromAsset(manager, context, mSoundFile);
        } catch (final IOException e) {
            mSound = null;
            Debug.e(e);
        }
    }

    public void play() {
        if (mSound != null && (mSoundConditioner == null || mSoundConditioner.enableSound())) {
            mSound.play();
        }
    }

}
