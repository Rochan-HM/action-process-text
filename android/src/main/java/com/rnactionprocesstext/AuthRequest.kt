package com.rnactionprocesstext

data class AuthRequest(override val url: String, override val body: Any?) : Request

data class RefreshTokenRequest(override val url: String, override val body: RefreshTokenRequestBody) : Request

data class RefreshTokenRequestBody(val refreshToken: String)

data class RefreshTokenResponse(val accessToken: String)
