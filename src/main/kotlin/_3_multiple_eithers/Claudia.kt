package _3_multiple_eithers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import kotlin.random.Random

object Claudia {

    data class Cluster(val name: String, val available: Boolean, val packageName: String? = null)

    private val clusters: MutableList<Cluster> = mutableListOf(
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

    fun lockCluster(appName: String): Either<Error, Cluster> {
        val cluster = clusters
           .find { c -> c.available }
           .rightIfNotNull { Error("Couldn't lock a cluster for $appName. All clusters are being used") }
        cluster.fold({}, { this.modifyCluster(it, available = false) })
        return cluster
    }

    fun deploy(cluster: Cluster, packageName: String): Either<Error, String> {
        val randomInt = Random.nextInt(1, 7)
//        val randomInt = Random.nextInt(1, 8)

        return if (randomInt != 7) {
            this.modifyCluster(cluster, cluster.available, packageName = packageName)
            "OK".right()
        } else {
            Error("Lucky number Slevin: Error deploying $packageName to ${cluster.name}").left()
        }
    }

    // Esta operación implica un side-effect.
    // Existe una forma funcional de manejar estado y es a través del uso de la mónada State.
    private fun modifyCluster(cluster: Cluster, available: Boolean, packageName: String? = null) {
        clusters.remove(cluster)
        val modifiedCluster = cluster.copy(available = available, packageName = packageName)
        clusters.add(modifiedCluster)
    }
}
