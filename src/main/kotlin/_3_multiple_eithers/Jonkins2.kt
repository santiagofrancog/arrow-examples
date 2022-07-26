package _3_multiple_eithers

import arrow.core.Either
import arrow.core.flatten
import arrow.core.sequence
import arrow.core.traverse
import arrow.core.zip
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object Jonkins2 {

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

    private fun buildBackend(): Either<JonkinsError, BuildResult> =
        buildApp("back", "BACK-1")

    private fun buildMailerApp(): Either<JonkinsError, List<BuildResult>> {
        val static = buildApp("mailer", "MAILER-STATIC-1")
        val apm = buildApp("mailer", "MAILER-1")
        val deploys = listOf(static, apm)
        return deploys.sequence()
    }

    private fun buildMailerAppTraverse(): Either<JonkinsError, List<BuildResult>> {
        val versions = listOf("MAILER-STATIC-2", "MAILER-2")
        return versions.traverse { version -> buildApp("mailer", version) }
    }

    private fun buildFrontend(): Either<JonkinsError, List<BuildResult>> {
        val versions = listOf("FRONT-STATIC-1", "FRONT-1")
        return versions.traverse { version -> buildApp("front", version) }
    }

    private fun buildApp(appName: String, appVersion: String): Either<JonkinsError, BuildResult> {
        return runBlocking {
            coroutineScope {
                val start = System.currentTimeMillis()

                val packageNameDef = async { releaseApp(appName, appVersion) }
                val clusterDef  = async { lockCluster(appName) }
                val packageName = packageNameDef.await()
                val cluster = clusterDef.await()

                val end = System.currentTimeMillis()
                println("release and lock: ${end - start}")

                val buildResult = packageName.zip(cluster) { pn, c ->
                    Claudia.deploy(cluster = c, packageName = pn).map { _ ->
                        BuildResult(
                            appName = appName,
                            packageName = pn,
                            cluster = c.name
                        )
                    }.mapLeft { th -> JonkinsError.DeployError(cluster = c.name, packageName = pn, error = th) }
                }

                buildResult.flatten()
            }
        }
    }

    private suspend fun releaseApp(appName: String, appVersion: String): Either<JonkinsError, String> {
        println("Init release")
        val millis = Random.nextLong(1000, 5000)
        delay(millis)
        println("Release finished after sleep $millis millis")
        return Claudia.release(appName, appVersion)
            .mapLeft { th -> JonkinsError.ReleaseTimeoutError(appName, appVersion, th) }
    }

    private suspend fun lockCluster(appName: String): Either<JonkinsError, Claudia.Cluster> {
        println("Init lock")
        val millis = Random.nextLong(1000, 5000)
        delay(millis)
        println("Lock finished after sleep $millis millis")
        return Claudia.lockCluster(appName).mapLeft { th -> JonkinsError.LockClusterError(appName, th) }
    }
}
