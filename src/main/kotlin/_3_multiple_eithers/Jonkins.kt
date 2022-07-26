package _3_multiple_eithers

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.sequence
import arrow.core.traverse
import arrow.core.zip

interface Jonkins {
    fun buildApp(appName: String, appVersion: String): Either<Error, BuildResult>
}

class ArrowJonkins : Jonkins {

    private fun buildBackend(): Either<Error, BuildResult> =
        buildApp("back", "BACK-1")

    private fun buildFrontend(): Either<Error, List<BuildResult>> {
        val static = buildApp("front", "FRONT-STATIC-1")
        val apm = buildApp("front", "FRONT-1")
        val deploys = listOf(static, apm)
        return deploys.sequence()
    }

    /**
     * https://hackage.haskell.org/package/base-4.16.2.0/docs/Data-Traversable.html
     * class (Functor t, Foldable t) => Traversable t where
     * sequenceA :: Applicative f => t (f a) -> f (t a)
     * */
    private fun buildMailerApp(): Either<Error, List<BuildResult>> {
        val static = buildApp("mailer", "MAILER-STATIC-1")
        val apm = buildApp("mailer", "MAILER-1")
        val deploys = listOf(static, apm)
        return deploys.sequence()
    }

    /**
     * https://hackage.haskell.org/package/base-4.16.2.0/docs/Data-Traversable.html
     * class (Functor t, Foldable t) => Traversable t where
     * traverse :: Applicative f => (a -> f b) -> t a -> f (t b)
     * */
    private fun buildMailerAppTraverse(): Either<Error, List<BuildResult>> {
        val versions = listOf("MAILER-STATIC-2", "MAILER-2")
        return versions.traverse { version -> buildApp("mailer", version) }
    }

    override fun buildApp(appName: String, appVersion: String): Either<Error, BuildResult> {
        val packageName: Either<Error, String> = Claudia.release(appName, appVersion)

        val cluster: Either<Error, Claudia.Cluster> = packageName.flatMap { _ -> Claudia.lockCluster(appName) }

        val deployResult: Either<Error, String> = packageName.flatMap { pn ->
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

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val j = ArrowJonkins()

            val buildBackendResult = j.buildBackend()
            println(buildBackendResult); println() // ~OK

            val buildMailerResult = j.buildMailerApp()
            println(buildMailerResult); println() // ~OK

            val buildFrontendResult = j.buildFrontend()
            println(buildFrontendResult); println() // TO

            val buildMailerResult2 = j.buildMailerAppTraverse()
            println(buildMailerResult2); println() // NO CLUSTER
        }
    }

}
