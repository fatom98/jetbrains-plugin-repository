package entity

import HttpRequestManager
import ObjectMapperProvider
import com.fasterxml.jackson.module.kotlin.readValue
import data.PluginId
import java.net.URI
import java.nio.file.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

data class Plugin(private val _pluginUrl: String) {

    lateinit var name: String
    lateinit var xmlId: String
    lateinit var description: String
    lateinit var version: String
    lateinit var notes: String
    lateinit var since: String
    lateinit var until: String
    lateinit var file: String

    private val pluginId = PluginId(_pluginUrl)
    private lateinit var data: ByteArray
    private lateinit var path: Path
    private lateinit var compatibleVersions: Map<String, String>

    init {
        val apiInfo = getApiInfo()
        setApiInfoValues(apiInfo)

        val releaseInfo = getReleaseInfo()
        setReleaseInfoValues(releaseInfo)
    }

    fun isCompatible(ide: String): Boolean {
        return compatibleVersions.keys.contains(ide)
    }

    fun download(outDir: String) {
        path = Path.of("${outDir}/plugins/${file}")

        if (path.exists()) {
            return
        }

        val uri = URI("${HttpRequestManager.DOWNLOAD_URL}/${file}")
        data = HttpRequestManager.sendGetRequest(uri)

        writeToFile()
    }

    private fun getApiInfo(): PluginApiInfo {
        val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}")
        val apiInfo = HttpRequestManager.sendGetRequest(uri)

        return ObjectMapperProvider.jsonMapper.readValue<PluginApiInfo>(apiInfo)
    }

    private fun getReleaseInfo(): PluginReleaseInfo {
        val uri = URI("${HttpRequestManager.API_URL}/${pluginId.value}/updates")
        val releaseInfo = HttpRequestManager.sendGetRequest(uri)

        val allReleases =
            ObjectMapperProvider.jsonMapper.readValue<List<PluginReleaseInfo>>(releaseInfo)
        return getLatestRelease(allReleases)
    }

    private fun setApiInfoValues(apiInfo: PluginApiInfo) {
        name = apiInfo.name
        description = apiInfo.description
        xmlId = apiInfo.xmlId
    }

    private fun setReleaseInfoValues(releaseInfo: PluginReleaseInfo) {
        version = releaseInfo.version.value
        notes = releaseInfo.notes
        since = releaseInfo.since
        until = releaseInfo.until.value
        file = releaseInfo.file
        compatibleVersions = releaseInfo.compatibleVersions
    }

    private fun writeToFile() {
        path.createParentDirectories().toFile().writeBytes(data)
    }

    companion object {
        private fun getLatestRelease(allReleases: List<PluginReleaseInfo>): PluginReleaseInfo {
            return allReleases.max()
        }
    }

    override fun toString(): String {
        return "$name ($version)"
    }

}
