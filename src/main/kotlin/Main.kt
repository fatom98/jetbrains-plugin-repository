import com.fasterxml.jackson.module.kotlin.readValue
import entity.Config
import entity.PluginsFile
import java.io.File


fun main() {
  val config = ObjectMapperProvider.yamlMapper.readValue<Config>(File("src/main/resources/config.yaml"))

  val plugins = config.plugins

  if (plugins.isEmpty()) {
    println("There are no plugins to host")
    return
  }

  val pluginsFile = PluginsFile(config.serverUrl)

  plugins.forEach { plugin ->
    plugin.download()
    pluginsFile.addPlugin(plugin)
  }

  pluginsFile.createFile()
}