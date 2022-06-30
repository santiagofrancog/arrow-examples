package _2_custom_errors

sealed class HttpStatusError(val statusCode: Int)

sealed class Redirection(statusCode: Int) : HttpStatusError(statusCode)
object MovedPermanently : Redirection(301)
object NotModified : Redirection(304)

sealed class ClientError(statusCode: Int) : HttpStatusError(statusCode)
object BadRequest : ClientError(400)
object NotFound : ClientError(404)
object IAmATeapot : ClientError(418)

sealed class ServerError(statusCode: Int) : HttpStatusError(statusCode)
object InternalServerError : ServerError(500)

class UnexpectedStatus(statusCode: Int) : HttpStatusError(statusCode)
