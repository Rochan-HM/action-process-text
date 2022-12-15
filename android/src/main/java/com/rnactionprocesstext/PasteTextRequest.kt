package com.rnactionprocesstext


import com.github.kittinunf.fuel.core.ResponseDeserializable

import com.google.gson.Gson

data class PasteTextRequest(
  val deserializer: ResponseDeserializable<PasteTextResponse> = PasteTextResponse.Deserializer(),
  override val url: String,
  override val body: Nothing? = null
): Request

data class PasteTextResponse(val text: String) {
  class Deserializer : ResponseDeserializable<PasteTextResponse> {
    override fun deserialize(content: String): PasteTextResponse? =
      Gson().fromJson(content, PasteTextResponse::class.java)
  }
}
