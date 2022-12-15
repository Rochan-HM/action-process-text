package com.rnactionprocesstext


data class CopyTextRequest(override val body: CopyTextRequestBody, override val url: String) : Request

data class CopyTextRequestBody(val text: String, val readonly: Boolean = false)
