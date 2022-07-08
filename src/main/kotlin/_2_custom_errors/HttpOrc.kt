package _2_custom_errors

object HttpOrc {
    @JvmStatic
    fun main(args: Array<String>) {
        val pepitaResponseOrError = PepitaClient.retrieve() // forzar 200
        println(pepitaResponseOrError); println()

        val sarazaResponseOrError = SarazaClient.retrieve() // forzar 200
        println(sarazaResponseOrError); println()

        val pepitaTeapot = PepitaClient.retrieve(418)
        println(pepitaTeapot); println()

        val sarazaBadRequest = SarazaClient.retrieve(400)
        println(sarazaBadRequest); println()

        val sarazaNotFound = SarazaClient.retrieve(404)
        println(sarazaNotFound); println()

        val sarazaInternalServerError = SarazaClient.retrieve(500)
        println(sarazaInternalServerError); println()

        val sarazaGenericError = SarazaClient.retrieve(777)
        println(sarazaGenericError); println()
    }
}
