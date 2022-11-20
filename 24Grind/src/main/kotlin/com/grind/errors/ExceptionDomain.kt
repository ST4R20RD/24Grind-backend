package com.grind.errors


class ErrorResponse(
    val status: Int,
    private val error: ErrorCode
) {
    val errorCode = error.code
    val message = error.message
}

enum class ErrorCode(val code: String, val message: String) {
    FILE_SIZE_TOO_LARGE("be_0001", "The given file is too large! Max file size is 8MB.")
}