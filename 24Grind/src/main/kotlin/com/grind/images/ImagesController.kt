package com.grind.images

import com.grind.Application.Companion.cloudinaryKey
import com.grind.Application.Companion.cloudinarySecret
import com.grind.errors.ErrorCode.ERROR_UPLOADING_IMAGE
import com.grind.errors.ErrorResponse
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@RestController
@RequestMapping
class ImagesController {

    val restTemplate = RestTemplate()

    companion object {
        const val eager = "c_pad,h_300,w_300"
        const val folderName = "grind"
    }

    @PostMapping("/v1/upload")
    fun uploadImage(@RequestParam("file") image: MultipartFile): ResponseEntity<Any> {
        val requestEntity = createHttpEntity(image)

        try {
            val response = restTemplate.exchange(
                "https://api.cloudinary.com/v1_1/dpa5mddyx/image/upload",
                HttpMethod.POST,
                requestEntity,
                CloudinaryResponse::class.java
            )

            if (response.statusCode != HttpStatus.OK) {
                return ResponseEntity.badRequest().body(ErrorResponse(response.statusCodeValue, ERROR_UPLOADING_IMAGE))
            }
            return ResponseEntity.ok(response.body)

        } catch (ex: RestClientException) {
            println(ex.localizedMessage)
            return ResponseEntity.badRequest().body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ERROR_UPLOADING_IMAGE))
        }
    }

    private fun createHttpEntity(image: MultipartFile): HttpEntity<MultiValueMap<String, Any>> {
        val timestamp = System.currentTimeMillis() / 1000
        val signature = getSignature(timestamp)
        val params = addParams(image, timestamp, signature)
        val headers = HttpHeaders().also { it.contentType = MediaType.MULTIPART_FORM_DATA }
        return HttpEntity<MultiValueMap<String, Any>>(params, headers)
    }

    private fun addParams(image: MultipartFile, timestamp: Long, signature: String): MultiValueMap<String, Any> {
        val params = LinkedMultiValueMap<String, Any>()
        params.add("file", image.resource)
        params.add("api_key", cloudinaryKey)
        params.add("eager", eager)
        params.add("folder", folderName)
        params.add("timestamp", timestamp)
        params.add("signature", signature)
        return params
    }

    private fun getSignature(timestamp: Long): String {
        val signatureTemplate = "eager=$eager&folder=$folderName&timestamp=$timestamp$cloudinarySecret"
        return MessageDigest.getInstance("SHA-1").digest(signatureTemplate.toByteArray()).joinToString("") {
            "%02x".format(it)
        }
    }
}