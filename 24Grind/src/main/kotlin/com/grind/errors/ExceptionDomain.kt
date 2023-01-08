package com.grind.errors


class ErrorResponse(
    val status: Int,
    private val error: ErrorCode
) {
    val errorCode = error.code
    val message = error.message
}

enum class ErrorCode(val code: String, val message: String) {
    // Backend errors
    FILE_SIZE_TOO_LARGE("be_0001", "The given file is too large! Max file size is 8MB."),
    USER_NOT_AUTHENTICATED("be_0002", "User must be authenticated."),
    CANNOT_EDIT_OTHER_USERS("be_0003", "User not allowed to edit other users."),
    CANNOT_CREATE_CARDS_FOR_OTHER_USERS("be_0004", "User not allowed to create cards for other users."),
    WRONG_USER_OR_PASSWORD("be_0005", "Wrong username or password."),

    // Cloudinary errors
    ERROR_UPLOADING_IMAGE("cl_0001", "Error uploading image to cloudinary")
}