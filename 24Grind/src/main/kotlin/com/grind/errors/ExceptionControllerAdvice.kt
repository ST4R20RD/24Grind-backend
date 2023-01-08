package com.grind.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleMaxSizeException(ex: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        val errorMessage = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ErrorCode.FILE_SIZE_TOO_LARGE
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}