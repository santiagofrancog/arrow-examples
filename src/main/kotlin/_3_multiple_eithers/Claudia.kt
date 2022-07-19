package _3_multiple_eithers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.rightIfNotNull
import arrow.core.zip
import kotlin.random.Random

object Claudia {

    data class Cluster(val name: String, val available: Boolean, val packageName: String? = null)

    private val clusters: MutableList<Cluster> = mutableListOf(
        Cluster("cluster-1", true),
        Cluster("cluster-2", true),
        Cluster("cluster-3", true),
        Cluster("cluster-4", true)
    )

    /**
     * Devuelve un Either.Right con el nombre de la versión generada,
     * o un Either.Left con un error en caso de querer releasear front-static
     * */
    fun release(appName: String, appVersion: String): Either<Error, String> {
        return if (appName == "front" && appVersion.contains("static", ignoreCase = true))
            Error("Timeout creating release for $appName-$appVersion").left()
        else
            return "$appName-$appVersion".right()
    }

    /**
     * Devuelve un Either.Right con un Cluster en caso de conseguir alguno disponible
     * o un Either.Left con un error en caso de que todos los clusters estén en uso.
     * Esta función tiene side-effect, ya que el manejo de estado en este objeto no se hace de forma funcional.
     * */
    fun lockCluster(appName: String): Either<Error, Cluster> {
        val cluster = clusters
           .find { c -> c.available }
           .rightIfNotNull { Error("Couldn't lock a cluster for $appName. All clusters are being used") }

        return cluster.map { old ->
            val new = old.copy(available = false)
            this.updateClusterInfo(old, new) // No hagan esto en sus casas
            new
        }
    }

    /**
     * Hace un deploy de un artefacto en un cluster y devuelve un Either.Right en caso de que haya salido "ok",
     * o un Either.Left si el deploy falló por las casualidades de la vida.
     * */
    fun deploy(cluster: Cluster, packageName: String): Either<Error, String> {
        val randomInt = Random.nextInt(1, 7)
//        val randomInt = Random.nextInt(1, 8)

        return if (randomInt != 7) {
            val newCluster = cluster.copy(packageName=packageName)
            this.updateClusterInfo(cluster, newCluster)
            "OK".right()
        } else {
            Error("Lucky number Slevin: Error deploying $packageName to ${cluster.name}").left()
        }
    }

    // Esta operación implica un side-effect.
    // Existe una forma funcional de manejar estado y es a través del uso de la mónada State.
    private fun updateClusterInfo(old: Cluster, new: Cluster) {
        clusters.remove(old)
        clusters.add(new)
    }
}
