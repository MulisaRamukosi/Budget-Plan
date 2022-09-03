package com.puzzle.industries.domain.common.response

import java.lang.Exception

data class Response<R>(
    val response: R,
    var message: String? = null,
    var exception: Exception? = null
)