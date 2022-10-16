package com.rnactionprocesstext


import android.util.Log

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.extensions.jsonBody

import com.google.gson.Gson


class Requests {
  companion object {
    @JvmStatic
    fun post(
      req: CopyTextRequest,
      successCallback: (String) -> Unit,
      errorCallback: (FuelError) -> Unit
    ) {
      Fuel.post(req.url).jsonBody(Gson().toJson(req.body).toString())
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
      Fuel.get(req.url).responseObject(req.deserializer) { request, response, result ->
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
