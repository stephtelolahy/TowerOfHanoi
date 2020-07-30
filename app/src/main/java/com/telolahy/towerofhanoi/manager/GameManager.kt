package com.telolahy.towerofhanoi.manager

import android.content.Context
import android.content.SharedPreferences
import com.telolahy.towerofhanoi.Constants.LEVELS_COUNT

/**
 * Created by stephanohuguestelolahy on 11/8/14.
 */
class GameManager {
  fun currentLevel(): Int {
    val prefs = preferences()
    return prefs.getInt(LEVEL_PREFS_KEY, 1)
  }

  fun saveCurrentLevel(level: Int) {
    val prefs = preferences()
    val edit = prefs.edit()
    edit.putInt(LEVEL_PREFS_KEY, level)
    edit.apply()
  }

  val isOnLastLevel: Boolean
    get() = currentLevel() == LEVELS_COUNT

  private fun preferences(): SharedPreferences {
    return ResourcesManager.instance.activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  companion object {
    val instance = GameManager()
    private const val LEVEL_PREFS_KEY = "level"
    private const val PREFS_NAME = "game_preferences"
  }
}