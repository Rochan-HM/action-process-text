package com.rnactionprocesstext


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.annotation.RequiresApi

import com.facebook.react.ReactActivity


class ActionProcessTextPasteActivity : ReactActivity() {

  private lateinit var storage: Storage

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    storage = Storage(this)

    processText(intent)
  }

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    storage = Storage(this)

    processText(intent)
  }

  @RequiresApi(Build.VERSION_CODES.M)
  private fun processText(intent: Intent) {
    var readonly = false
    try {
      if (intent.action != null && intent.action.equals(Intent.ACTION_PROCESS_TEXT)) {
        if (intent.hasExtra(Intent.EXTRA_PROCESS_TEXT)) {
          readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)
        }
      }

      if (readonly) {
        Toast.makeText(this, "Unable to paste text here!", Toast.LENGTH_SHORT).show()
        finish()
        return
      }

      val url: String = storage.get("url").let { it ?: "http://10.0.2.2" }

      val req = PasteTextRequest(
        url = "$url/paste",
      )
      Requests.get(req, { result ->
        Log.d("Result", result.toString())
        setResponseText(result.text)
      }, { error ->
        Log.d("Error", error.toString())
        setResponseText(null)
      })
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @RequiresApi(Build.VERSION_CODES.M)
  private fun setResponseText(text: String?) {
    try {
      val intent = Intent()
      if (text!!.isNotEmpty()) {
        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, text)
        setResult(RESULT_OK, intent)
        Toast.makeText(this, "Text pasted!", Toast.LENGTH_SHORT).show()
      } else {
        setResult(RESULT_CANCELED, intent)
        Toast.makeText(this, "Unable to paste text!", Toast.LENGTH_SHORT).show()
      }
    } catch (e: Exception) {
      Toast.makeText(this, "Sorry! Something went wrong!", Toast.LENGTH_SHORT).show()
      e.printStackTrace()
    } finally {
      finish()
    }
  }
}
