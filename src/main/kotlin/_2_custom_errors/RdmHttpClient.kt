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

    /**
     * Devuelve aleatoriamente un código de los definidos en HTTP_STATUS_CODES.
     * Si el código es de la familia de los 2XX, devuelve un Either.Right con un String simulando un body
     * Si el código es un redirect o un error, devuelve un Either.Left con un objeto de modelo representando el status code
     * Si recibe por parámetro forcedStatus, aplica la misma lógica pero salteando el sorteo.
     * */
    fun retrieve(forcedStatus: Int? = null): Either<HttpStatusError, String> {
        val httpStatus = forcedStatus ?: run {
            val index = Random.nextInt(0, HTTP_STATUS_CODES.size - 1)
            HTTP_STATUS_CODES[index]
        }

        return httpStatus
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
