package com.telolahy.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import com.telolahy.towerofhanoi.R
import com.telolahy.towerofhanoi.manager.ResourcesManager

/**
 * Created by stephanohuguestelolahy on 12/7/14.
 */
object AppRater {
  private const val DAYS_UNTIL_PROMPT = 0
  private const val LAUNCHES_UNTIL_PROMPT = 2

  @JvmStatic
  fun checkAppLaunched() {
    val context: Context = ResourcesManager.instance.activity
    val prefs = context.getSharedPreferences("apprater", 0)
    if (prefs.getBoolean("dontshowagain", false)) {
      return
    }
    val editor = prefs.edit()

    // Increment launch counter
    val launch_count = prefs.getLong("launch_count", 0) + 1
    editor.putLong("launch_count", launch_count)

    // Get date of first launch
    var date_firstLaunch = prefs.getLong("date_firstlaunch", 0)
    if (date_firstLaunch == 0L) {
      date_firstLaunch = System.currentTimeMillis()
      editor.putLong("date_firstlaunch", date_firstLaunch)
    }

    // Wait at least n days before opening
    if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
      if (System.currentTimeMillis() >= date_firstLaunch +
          DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
        (context as Activity).runOnUiThread { showRateDialog(context, editor) }
      }
    }
    editor.commit()
  }

  fun showRateDialog(context: Context, editor: SharedPreferences.Editor?) {
    val appName = context.resources.getString(R.string.app_name)
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.resources.getString(R.string.rate) + " " + appName)
    builder.setMessage(context.resources.getString(R.string.if_you_enjoy, appName))
    builder.setPositiveButton(context.resources.getString(R.string.rate)) { dialogInterface, i ->
      if (editor != null) {
        editor.putBoolean("dontshowagain", true)
        editor.commit()
      }
      val packageName = context.applicationContext.packageName
      context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    }
    builder.setNeutralButton(context.resources.getString(R.string.remind_me_later)) { dialogInterface, i -> }
    builder.setNeutralButton(context.resources.getString(R.string.remind_me_later)) { dialogInterface, i -> }
    builder.setNegativeButton(context.resources.getString(R.string.no_thanks)) { dialogInterface, i ->
      if (editor != null) {
        editor.putBoolean("dontshowagain", true)
        editor.commit()
      }
    }
    builder.setCancelable(false)
    builder.show()
  }
}