package com.rnactionprocesstext

import android.util.Base64
import java.io.UnsupportedEncodingException


class JWTUtils {
  companion object {
    @JvmStatic
    fun decoded(JWTEncoded: String): String {
      try {
        val split = JWTEncoded.split("\\.").toTypedArray()
        return getJson(split[1])
      } catch (e: UnsupportedEncodingException) {
        throw java.lang.Exception("Invalid token")
      }
    }

    private fun getJson(strEncoded: String): String {
      val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
      return String(decodedBytes, charset("UTF-8"))
    }
  }

}
