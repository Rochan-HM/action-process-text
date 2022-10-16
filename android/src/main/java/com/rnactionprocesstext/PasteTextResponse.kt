package com.rnactionprocesstext


import com.github.kittinunf.fuel.core.ResponseDeserializable

import com.google.gson.Gson

data class PasteTextRequest(
  val deserializer: ResponseDeserializable<PasteTextResponse> = PasteTextResponse.Deserializer(),
  val url: String
)

data class PasteTextResponse(val text: String) {
  class Deserializer : ResponseDeserializable<PasteTextResponse> {
    override fun deserialize(content: String): PasteTextResponse? =
      Gson().fromJson(content, PasteTextResponse::class.java)
  }
}
