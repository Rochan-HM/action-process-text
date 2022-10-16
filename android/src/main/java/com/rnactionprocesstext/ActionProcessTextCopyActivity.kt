package com.rnactionprocesstext


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.annotation.RequiresApi

import com.facebook.react.ReactActivity

class ActionProcessTextCopyActivity : ReactActivity() {

  private lateinit var storage: Storage

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    storage = Storage(this)

    processText(intent)
  }

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    storage = Storage(this)

    if (intent != null) {
      processText(intent)
    }
  }


  @RequiresApi(Build.VERSION_CODES.M)
  private fun processText(intent: Intent) {
    var text: CharSequence? = null
    var readonly = false
    try {
      if (intent.action != null && intent.action.equals(Intent.ACTION_PROCESS_TEXT)) {
        if (intent.hasExtra(Intent.EXTRA_PROCESS_TEXT)) {
          text = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT)
          readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)
        }
      }

      val url: String = storage.get("url").let { it ?: "http://10.0.2.2" }

      val req = CopyTextRequest(
        url = "$url/copy",
        body = CopyTextRequestBody(text = text.toString(), readonly = readonly)
      )
      Requests.post(req, { result ->
        Log.d("Result", result)
        Toast.makeText(this, "Copied text!", Toast.LENGTH_SHORT).show()
      }, { error ->
        Log.d("Error", error.toString())
        Toast.makeText(this, "Unable to copy text!", Toast.LENGTH_SHORT).show()
      })
    } catch (e: Exception) {
      e.printStackTrace()
    } finally {
      finish()
    }
  }
}
