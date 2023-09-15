import entity.Config
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories

class PluginManager(private val config: Config) {

  fun build() {
    config.plugins.forEach { plugin ->
      plugin.download()
      writeToFile(Path.of(plugin.file), plugin.data)
    }
  }

  private fun writeToFile(path: Path, content: String) {
    path.parent.createDirectories()
    val file = File(path.toString())

    file.createNewFile()
    file.writeText(content)
  }

}
