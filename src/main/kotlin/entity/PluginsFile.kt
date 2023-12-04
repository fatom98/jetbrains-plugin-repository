package entity

import java.io.File

class PluginsFile(private val config: Config) {

    private val header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<plugins>\n"
    private val footer = "\n</plugins>"
    private var allPluginsMetaData = ""

    fun addPlugin(plugin: Plugin) {
        allPluginsMetaData += """
    <plugin id="${plugin.xmlId}" url="${config.serverUrl}/files/${plugin.file}" version="${plugin.version}">
      <name> ${plugin.name} </name>
      <description> <![CDATA[${plugin.description}]]> </description>
      <change-notes> <![CDATA[${plugin.notes} ]]> </change-notes>
      <idea-version since-build="${plugin.since}" until-build="${plugin.until}"/>
    </plugin>
    """.trimIndent()
    }

    fun createFile() {
        val content = header + allPluginsMetaData + footer

        val file = File("${config.outDir}/updatePlugins.xml")
        file.createNewFile()
        file.writeText(content)
    }

}