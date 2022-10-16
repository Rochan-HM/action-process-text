package com.rnactionprocesstext


data class CopyTextRequest(val body: CopyTextRequestBody, val url: String)

data class CopyTextRequestBody(val text: String, val readonly: Boolean = false)
