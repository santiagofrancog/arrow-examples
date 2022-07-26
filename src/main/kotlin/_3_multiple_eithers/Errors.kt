package _3_multiple_eithers

sealed class JonkinsError(val message: String, val error: Throwable) {

    override fun toString(): String {
        return "$message. Ex.: $error"
    }

    class ReleaseTimeoutError(appName: String, appVersion: String, error: Throwable) :
        JonkinsError("Jonkins failed releasing $appName-$appVersion due a timeout", error)

    class LockClusterError(appName: String, error: Throwable) :
        JonkinsError("Jonkins failed locking for $appName", error)

    class DeployError(cluster: String, packageName: String, error: Throwable) :
        JonkinsError("Jonkins failed deploying for $packageName to $cluster", error)
}
