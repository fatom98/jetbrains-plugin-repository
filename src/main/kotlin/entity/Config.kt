package entity

data class Config(
    val serverUrl: String,
    val outDir: String,
    val ide: String,
    val plugins: List<Plugin>
)