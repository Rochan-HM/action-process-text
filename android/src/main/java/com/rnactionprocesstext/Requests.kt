package com.rnactionprocesstext


import android.util.Log

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.extensions.jsonBody

import com.google.gson.Gson

interface Request {
    val url: String
    val body: Any?
}

class Requests {
  companion object {
    private const val TIMEOUT = 3000

    @JvmStatic
    fun post(
      req: Request,
      successCallback: (String) -> Unit,
      errorCallback: (FuelError) -> Unit
    ) {
      Fuel.post(req.url).timeout(TIMEOUT).timeoutRead(TIMEOUT)
        .jsonBody(Gson().toJson(req.body).toString())
        .response { request, response, result ->
          Log.d("Request", request.toString())
          Log.d("Response", response.toString())

          when (result) {
            is com.github.kittinunf.result.Result.Success -> {
              successCallback(result.toString())
            }
            is com.github.kittinunf.result.Result.Failure -> {
              errorCallback(result.getException())
            }
          }
        }
    }

    @JvmStatic
    fun get(
      req: PasteTextRequest,
      successCallback: (PasteTextResponse) -> Unit,
      errorCallback: (FuelError) -> Unit
    ) {
      Fuel.get(req.url).timeout(TIMEOUT).timeoutRead(TIMEOUT)
        .responseObject(req.deserializer) { request, response, result ->
          Log.d("Request", request.toString())
          Log.d("Response", response.toString())

          when (result) {
            is com.github.kittinunf.result.Result.Success -> {
              successCallback(result.value)
            }
            is com.github.kittinunf.result.Result.Failure -> {
              errorCallback(result.getException())
            }
          }
        }
    }
  }
}
