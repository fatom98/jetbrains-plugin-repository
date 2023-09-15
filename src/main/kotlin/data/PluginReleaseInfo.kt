package data

data class PluginReleaseInfo(
  val version: String,
  val notes: String,
  val since: String,
  val until: String,
  val file: String
)
