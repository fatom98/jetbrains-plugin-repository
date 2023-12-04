package data

data class PluginId(private val pluginUrl: String) {

  val value = extractPluginId(pluginUrl)

  private fun extractPluginId(pluginUrl: String): String {
    return pluginUrl.split("-").first().split("/").last()
  }

}
