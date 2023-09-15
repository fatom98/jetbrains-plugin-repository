import com.fasterxml.jackson.module.kotlin.readValue
import entity.Config
import java.io.File

fun main() {
  val config = ObjectMapperProvider.yamlMapper.readValue<Config>(File("src/main/resources/config.yaml"))

  if (config.plugins.isEmpty()) {
    println("There are no plugins to host")
    return
  }

  PluginManager(config).build()
}
