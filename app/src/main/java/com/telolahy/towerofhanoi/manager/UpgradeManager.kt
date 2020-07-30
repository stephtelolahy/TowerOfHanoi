package com.telolahy.towerofhanoi.manager

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by stephanohuguestelolahy on 12/14/14.
 */
class UpgradeManager {
  fun checkUpgrade() {
    checkUpgradeToVersion_1_2()
  }

  private fun checkUpgradeToVersion_1_2() {
    if (!upgradedToVersion1_2()) {
      val currentLevel = GameManager.instance.currentLevel()
      if (currentLevel == 6) {
        GameManager.instance.saveCurrentLevel(7)
      }
      saveUpgradedToVersion1_2()
    }
  }

  private fun upgradedToVersion1_2(): Boolean {
    return preferences().getBoolean(UPGRADE_V_1_2__PREFS_KEY, false)
  }

  private fun saveUpgradedToVersion1_2() {
    val prefs = preferences()
    val edit = prefs.edit()
    edit.putBoolean(UPGRADE_V_1_2__PREFS_KEY, true)
    edit.apply()
  }

  private fun preferences(): SharedPreferences {
    return ResourcesManager.instance.activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  companion object {
    val instance = UpgradeManager()
    private const val UPGRADE_V_1_2__PREFS_KEY = "upgrade_v_1_2"
    private const val PREFS_NAME = "game_preferences"
  }
}