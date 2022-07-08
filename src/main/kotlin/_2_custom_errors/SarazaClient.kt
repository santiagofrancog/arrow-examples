package _2_custom_errors

import arrow.core.Either

object SarazaClient {

    fun retrieve(forcedStatus: Int? = null): Either<SarazaError, String> {
        return RdmHttpClient.retrieve(forcedStatus)
            .mapLeft { handleErrors(it) }
    }

    private fun handleErrors(httpStatusError: HttpStatusError): SarazaError = when (httpStatusError) {
        BadRequest -> SarazaBadRequest("Saraza bad request!!")
        NotFound -> SarazaNotFound("Saraza not found!!")
        InternalServerError -> SarazaInternalServerError("Saraza internal server error!!")
        else -> GenericSarazaError(
            statusCode = httpStatusError.statusCode,
            msg = "I'm okay with wrapping other errors within this class, so that's what I'm doing here"
        )
    }
}

sealed class SarazaError(private val statusCode: Int, private val msg: String) {
    override fun toString(): String {
        return "SarazaError(statusCode=$statusCode, msg='$msg')"
    }
}
class SarazaBadRequest(msg: String) : SarazaError(400, msg)
class SarazaNotFound(msg: String) : SarazaError(404, msg)
class SarazaInternalServerError(msg: String) : SarazaError(500, msg)
class GenericSarazaError(statusCode: Int, msg: String) : SarazaError(statusCode, msg)
