package data

@JvmInline
value class PluginId private constructor(val value: String) {

    companion object {

        fun of(pluginUrl: String): PluginId = PluginId(extractPluginId(pluginUrl))

        private fun extractPluginId(pluginUrl: String): String = pluginUrl
            .split("-")
            .first()
            .split("/")
            .last()
    }
}