package entity

import HttpRequestManager
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.readValue
import data.PluginId
import data.Until
import data.Version
import jsonMapper
import java.net.URI
import java.nio.file.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

data class Plugin(
    val pluginId: PluginId,
    val name: String,
    val xmlId: String,
    val description: String,
    val version: Version,
    val until: Until,
    val notes: String,
    val since: String,
    val file: String,
) {
    private lateinit var data: ByteArray
    private lateinit var path: Path
    private lateinit var compatibleVersions: Map<String, String>

    fun isCompatibleWith(ide: String): Boolean = compatibleVersions.keys.contains(ide)

    fun download(outDir: String) {

        path = Path.of("${outDir}/plugins/${file}")

        if (path.exists())
            return

        val uri = URI("${HttpRequestManager.DOWNLOAD_URL}/${file}")
        data = HttpRequestManager.sendGetRequest(uri)

        writeToFile()
    }

    private fun writeToFile() {
        path.createParentDirectories().toFile().writeBytes(data)
    }

    override fun toString(): String = "$name ($version)"

    companion object {

        @JsonCreator
        fun of(pluginUrl: String): Plugin {

            val pluginId = PluginId.of(pluginUrl)

            val apiInfo = getApiInfo(pluginId)
            val releaseInfo = getReleaseInfo(pluginId)

            return Plugin(
                pluginId,
                apiInfo.name,
                apiInfo.xmlId,
                apiInfo.description,
                releaseInfo.version,
                releaseInfo.until,
                releaseInfo.notes,
                releaseInfo.since,
                releaseInfo.file
            )
        }

        private fun getApiInfo(pluginId: PluginId): PluginApiInfo {
            val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}")
            val apiInfo = HttpRequestManager.sendGetRequest(uri)

            return jsonMapper.readValue<PluginApiInfo>(apiInfo)
        }

        private fun getReleaseInfo(pluginId: PluginId): PluginReleaseInfo {
            val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}/updates")
            val releaseInfo = HttpRequestManager.sendGetRequest(uri)

            val allReleases = jsonMapper.readValue<List<PluginReleaseInfo>>(releaseInfo)
            return getLatestRelease(allReleases)
        }

        private fun getLatestRelease(allReleases: List<PluginReleaseInfo>) = allReleases.max()
    }

}