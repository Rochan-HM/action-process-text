package com.rnactionprocesstext


import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RnActionProcessTextModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "RnActionProcessText"
  }


  @ReactMethod
  fun sharedPreferenceName(): String {
    return Storage.SHARED_PREFERENCES_NAME
  }

}
