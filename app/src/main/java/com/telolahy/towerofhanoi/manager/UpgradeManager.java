package com.telolahy.towerofhanoi.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stephanohuguestelolahy on 12/14/14.
 */
public class UpgradeManager {

    private static final UpgradeManager INSTANCE = new UpgradeManager();
    private static final String UPGRADE_V_1_2__PREFS_KEY = "upgrade_v_1_2";
    private static final String PREFS_NAME = "game_preferences";

    public static UpgradeManager getInstance() {
        return INSTANCE;
    }

    public void checkUpgrade() {

        checkUpgradeToVersion_1_2();
    }

    private void checkUpgradeToVersion_1_2() {

        if (!upgradedToVersion1_2()) {

            int currentLevel = GameManager.getInstance().currentLevel();
            if (currentLevel == 6) {
                GameManager.getInstance().saveCurrentLevel(7);
            }
            saveUpgradedToVersion1_2();
        }
    }

    public boolean upgradedToVersion1_2() {

        return preferences().getBoolean(UPGRADE_V_1_2__PREFS_KEY, false);
    }

    public void saveUpgradedToVersion1_2() {

        SharedPreferences prefs = preferences();
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(UPGRADE_V_1_2__PREFS_KEY, true);
        edit.commit();
    }

    private SharedPreferences preferences() {

        return ResourcesManager.getInstance().activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
