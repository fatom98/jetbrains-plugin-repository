package entity

import data.Until
import data.Version

data class PluginReleaseInfo(
  val version: Version,
  val notes: String,
  val since: String,
  val until: Until,
  val file: String,
  val cdate: String,
  val compatibleVersions: Map<String, String>
) : Comparable<PluginReleaseInfo> {

  override operator fun compareTo(other: PluginReleaseInfo): Int {
    var result = version.compareTo(other.version)
    if (result != 0) return result

    result = until.compareTo(other.until)
    if (result != 0) return result

    return (cdate.toLong() - other.cdate.toLong()).toInt()
  }

}