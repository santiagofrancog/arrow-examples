package _3_multiple_eithers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import kotlin.random.Random

object Claudia {

    data class Cluster(val name: String, val available: Boolean, val packageName: String? = null)

    private val availableClusters: MutableList<Cluster> = mutableListOf(
        Cluster("cluster-1", true),
        Cluster("cluster-2", true),
        Cluster("cluster-3", true),
        Cluster("cluster-4", true)
    )

    fun release(appName: String, appVersion: String): Either<Error, String> {
        return if (appName == "front" && appVersion.contains("static", ignoreCase = true))
            Error("Timeout creating release for $appName-$appVersion").left()
        else
            return "$appName-$appVersion".right()
    }

    fun lockCluster(appName: String): Either<Error, Cluster> =
        availableClusters
            .find { c -> c.available }
            .rightIfNotNull { Error("Couldn't lock a cluster for $appName. All clusters are being used") }

    fun deploy(cluster: Cluster, packageName: String): Either<Error, String> {
        val randomInt = Random.nextInt(1, 7)
//        val randomInt = Random.nextInt(1, 8)

        return if (randomInt != 7) {
            val lockedCluster = cluster.copy(available = false, packageName = packageName)
            availableClusters.remove(cluster)
            availableClusters.add(lockedCluster)
            "OK".right()
        } else {
            Error("Lucky number Slevin: Error deploying $packageName to ${cluster.name}").left()
        }
    }
}
