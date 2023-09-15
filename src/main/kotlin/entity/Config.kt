package entity

data class Config(val serverUrl: String, val directory: String, val plugins: List<Plugin>)
