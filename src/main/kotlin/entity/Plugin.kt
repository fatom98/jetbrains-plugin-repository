package entity

import HttpRequestManager
import ObjectMapperProvider
import com.fasterxml.jackson.module.kotlin.readValue
import data.PluginApiInfo
import data.PluginId
import data.PluginReleaseInfo
import java.net.URI

class Plugin(pluginUrl: String) {

  private val pluginId = PluginId(pluginUrl)

  lateinit var name: String
  lateinit var description: String
  lateinit var version: String
  lateinit var notes: String
  lateinit var since: String
  lateinit var until: String
  lateinit var file: String
  lateinit var data: String

  init {
    val apiInfo = getApiInfo()
    setApiInfoValues(apiInfo)

    val releaseInfo = getReleaseInfo()
    setReleaseInfoValues(releaseInfo)
  }

  fun download() {
    val uri = URI("${HttpRequestManager.DOWNLOAD_URL}/${file}")
    data = HttpRequestManager.sendGetRequest(uri)
  }

  private fun getApiInfo(): PluginApiInfo {
    val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}")
    val apiInfo = HttpRequestManager.sendGetRequest(uri)

    return ObjectMapperProvider.jsonMapper.readValue<PluginApiInfo>(apiInfo)
  }

  private fun getReleaseInfo(): PluginReleaseInfo {
    val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}/updates")
    val releaseInfo = HttpRequestManager.sendGetRequest(uri)

    return ObjectMapperProvider.jsonMapper.readValue<List<PluginReleaseInfo>>(releaseInfo).first()
  }

  private fun setApiInfoValues(apiInfo: PluginApiInfo) {
    name = apiInfo.name
    description = apiInfo.description
  }

  private fun setReleaseInfoValues(releaseInfo: PluginReleaseInfo) {
    version = releaseInfo.version
    notes = releaseInfo.notes
    since = releaseInfo.since
    until = releaseInfo.until
    file = releaseInfo.file
  }

}
