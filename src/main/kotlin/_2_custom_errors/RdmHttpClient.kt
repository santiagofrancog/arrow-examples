package _2_custom_errors

import arrow.core.Either
import arrow.core.filterOrOther
import arrow.core.rightIfNotNull
import kotlin.random.Random

object RdmHttpClient {
    private val HTTP_STATUS_CODES = listOf(
        200, 201, 202,
        301, 304,
        400, 404, 418,
        500
    )

    fun retrieve(forcedStatus: Int? = null): Either<HttpStatusError, String> {
        val index = forcedStatus?.let { fs ->
            HTTP_STATUS_CODES.find { fs == it }
        }?.let {
            HTTP_STATUS_CODES.indexOf(it)
        } ?: Random.nextInt(0, HTTP_STATUS_CODES.size - 1)

        return HTTP_STATUS_CODES[index]
            .rightIfNotNull { IAmATeapot }
            .filterOrOther(
                { statusCode -> !isRedirect(statusCode) && !isError(statusCode) },
                { statusCode -> handleStatusError(statusCode) }
            ).map { getBody(it) }
    }

    private fun isRedirect(statusCode: Int) =
        statusCode in 300..399

    private fun isError(statusCode: Int) =
        statusCode in 400..599

    private fun handleStatusError(statusCode: Int): HttpStatusError = when (statusCode) {
        301 -> MovedPermanently
        304 -> NotModified
        400 -> BadRequest
        404 -> NotFound
        418 -> IAmATeapot
        500 -> InternalServerError
        else -> UnexpectedStatus(statusCode = statusCode)
    }

    private fun getBody(statusCode: Int): String = when (statusCode) {
        200 -> "Ok"
        201 -> "Created"
        202 -> "Accepted"
        else -> "$statusCode: Success"
    }
}
