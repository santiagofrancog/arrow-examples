package _3_multiple_eithers

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.sequence
import arrow.core.traverse
import arrow.core.zip

object Jonkins {

    @JvmStatic
    fun main(args: Array<String>) {
        val buildBackendResult = buildBackend()
        println(buildBackendResult); println() // ~OK

        val buildMailerResult = buildMailerApp()
        println(buildMailerResult); println() // ~OK

        val buildFrontendResult = buildFrontend()
        println(buildFrontendResult); println() // TO

        val buildMailerResult2 = buildMailerAppTraverse()
        println(buildMailerResult2); println() // NO CLUSTER
    }

    private fun buildBackend(): Either<Error, BuildResult> =
        buildApp("back", "BACK-1")

    private fun buildMailerApp(): Either<Error, List<BuildResult>> {
        val static = buildApp("mailer", "MAILER-STATIC-1")
        val apm = buildApp("mailer", "MAILER-1")
        val deploys = listOf(static, apm)
        return deploys.sequence()
    }

    private fun buildMailerAppTraverse(): Either<Error, List<BuildResult>> {
        val versions = listOf("MAILER-STATIC-2", "MAILER-2")
        return versions.traverse { version -> buildApp("mailer", version) }
    }

    private fun buildFrontend(): Either<Error, List<BuildResult>> {
        val versions = listOf("FRONT-STATIC-1", "FRONT-1")
//        Borrar un cluster para mostrar como falla primero por falta de clusters
//        val versions = listOf("FRONT-1", "FRONT-STATIC-1")
        return versions.traverse { version -> buildApp("front", version) }
    }

    private fun buildApp(appName: String, appVersion: String): Either<Error, BuildResult> {
        val packageName = Claudia.release(appName, appVersion)
        val cluster = Claudia.lockCluster(appName)
        val deployResult = packageName.flatMap { pn ->
            cluster.flatMap { c ->
                Claudia.deploy(cluster = c, packageName = pn)
            }
        }
        // Alternative to nested flatmaps
        return packageName.zip(cluster, deployResult) { pn, c, _ ->
            BuildResult(
                appName = appName,
                packageName = pn,
                cluster = c.name
            )
        }
    }
}
