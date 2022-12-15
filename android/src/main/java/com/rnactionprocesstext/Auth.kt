package com.rnactionprocesstext

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.google.gson.Gson

data class JWTToken(
  val aud: String,
  val exp: Long,
  val iat: Long,
  val iss: String,
  val jti: String,
  val nbf: Long,
  val sub: String,
  val typ: String
)

class Auth(storage: Storage, context: ReactApplicationContext?) {
  private val storage = storage
  private val context = context

  public fun getValidToken(): String? {
    val accessToken = storage.get("ACCESS_TOKEN")

    if (accessToken != null) {
      val decodedToken = Gson().fromJson(JWTUtils.decoded(accessToken), JWTToken::class.java)

      if (decodedToken.exp < System.currentTimeMillis() / 1000) {
        return accessToken
      }
    }

    val refreshToken = storage.get("REFRESH_TOKEN")
    if (refreshToken == null) {
      openLoginScreen()
      return null
    }

    val decodedToken = Gson().fromJson(JWTUtils.decoded(refreshToken), JWTToken::class.java)

    if (decodedToken.exp < System.currentTimeMillis() / 1000) {
      openLoginScreen()
      return null
    }

    val url = storage.get("SERVER_URL") ?: ""

    val req = RefreshTokenRequest(
      url = "$url/api/refresh",
      body = RefreshTokenRequestBody(refreshToken = refreshToken)
    )

    var token: String? = null

    Requests.post(req, { result ->
      val response = Gson().fromJson(result, RefreshTokenResponse::class.java)
      Log.d("RefreshTokenResponse", response.toString())
      storage.set("ACCESS_TOKEN", response.accessToken)
      token = response.accessToken
    }, { error ->
      Log.d("RefreshTokenError", error.toString())
      openLoginScreen()
      token = null
    })

    return token
  }

  private fun openLoginScreen() {

  }
}
