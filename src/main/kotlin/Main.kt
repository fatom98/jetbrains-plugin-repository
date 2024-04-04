import com.fasterxml.jackson.module.kotlin.readValue
import entity.Config
import entity.PluginsFile
import org.apache.logging.log4j.kotlin.Logging
import java.io.File

fun main() {

    val logger = Logger.logger

    val config = yamlMapper.readValue<Config>(File("src/main/resources/config.yaml"))
    val plugins = config.plugins

    if (plugins.isEmpty()) {
        logger.info("There are no plugins to host")
        return
    }

    val pluginsFile = PluginsFile(config)

    plugins.forEach { plugin ->

        if (!plugin.isCompatibleWith(config.ide)) {
            logger.debug(
                "$plugin is not compatible with ${config.ide}. " +
                        "Ignoring the plugin. Consider removing it from the plugins list."
            )
            return@forEach
        }

        plugin.download(config.outDir)
        pluginsFile.addPlugin(plugin)
    }

    pluginsFile.createFile()
}

object Logger : Logging