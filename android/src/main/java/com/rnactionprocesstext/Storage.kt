package com.rnactionprocesstext


import android.content.Context
import android.content.SharedPreferences


class Storage(context: Context) {
  companion object {
    const val SHARED_PREFERENCES_NAME = "syncer_settings"
  }

  private val settings: SharedPreferences
  private val editor: SharedPreferences.Editor

  init {
    settings = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    editor = settings.edit()
  }

  fun set(key: String, value: String) {
    editor.putString(key, value)
    editor.commit()
  }

  fun get(key: String): String? {
    return settings.getString(key, null)
  }
}
