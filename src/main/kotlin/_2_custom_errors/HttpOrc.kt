package _2_custom_errors

object HttpOrc {
    @JvmStatic
    fun main(args: Array<String>) {
        val pepitaResponseOrError = PepitaClient.retrieve() // forzar 200
        println("Ej. 1: $pepitaResponseOrError"); println()

        val pepitaTeapot = PepitaClient.retrieve(418)
        println("Ej. 2: $pepitaTeapot"); println()

        val pepitaGenericError = PepitaClient.retrieve(500)
        println("Ej. 3: $pepitaGenericError"); println()

        val sarazaResponseOrError = SarazaClient.retrieve() // forzar 200
        println("Ej. 4: $sarazaResponseOrError"); println()

        val sarazaBadRequest = SarazaClient.retrieve(400)
        println("Ej. 5: $sarazaBadRequest"); println()

        val sarazaNotFound = SarazaClient.retrieve(404)
        println("Ej. 6: $sarazaNotFound"); println()

        val sarazaInternalServerError = SarazaClient.retrieve(500)
        println("Ej. 7: $sarazaInternalServerError"); println()

        val sarazaGenericError = SarazaClient.retrieve(520)
        println("Ej. 8: $sarazaGenericError"); println()
    }
}
