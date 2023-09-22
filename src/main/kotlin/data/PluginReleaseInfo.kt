package data

data class PluginReleaseInfo(
  val version: Version,
  val notes: String,
  val since: String,
  val until: String,
  val sinceUntil: String,
  val file: String
) : Comparable<PluginReleaseInfo> {

  override operator fun compareTo(other: PluginReleaseInfo): Int {
    if (sinceUntil == other.sinceUntil) {
      return version.compareTo(other.version)
    }

    return sinceUntil.compareTo(other.sinceUntil)
  }
}
