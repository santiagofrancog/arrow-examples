package _2_custom_errors

import arrow.core.Either

object PepitaClient {

    fun retrieve(forcedStatus: Int? = null): Either<PepitaError, String> {
        return RdmHttpClient.retrieve(forcedStatus)
            .mapLeft { handleErrors(it) }
    }

    private fun handleErrors(httpStatusError: HttpStatusError): PepitaError = when (httpStatusError) {
        IAmATeapot -> PepitaIAmATeapot("I am Pepita's teapot")
        else -> GenericPepitaError(
            statusCode = httpStatusError.statusCode,
            msg = "I'm okay with wrapping other errors within this class, so that's what I'm doing here"
        )
    }
}

sealed class PepitaError(val statusCode: Int, val msg: String) {
    override fun toString(): String {
        return "PepitaError(statusCode=$statusCode, msg='$msg')"
    }
}
class PepitaIAmATeapot(msg: String) : PepitaError(418, msg)
class GenericPepitaError(statusCode: Int, msg: String) : PepitaError(statusCode, msg)
