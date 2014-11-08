package com.telolahy.towerofhanoi.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stephanohuguestelolahy on 11/8/14.
 */
public class GameManager {

    private static final GameManager INSTANCE = new GameManager();
    private static final String LEVEL_PREFS_KEY = "level";
    private static final String PREFS_NAME = "game_preferences";

    public static final int LEVELS_COUNT = 5;

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public int currentLevel() {

        SharedPreferences prefs = preferences();
        int level = prefs.getInt(LEVEL_PREFS_KEY, 1);
        return level;
    }

    public void saveCurrentLevel(int level) {

        SharedPreferences prefs = preferences();
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(LEVEL_PREFS_KEY, level);
        edit.commit();
    }

    private SharedPreferences preferences() {

        return ResourcesManager.getInstance().activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
