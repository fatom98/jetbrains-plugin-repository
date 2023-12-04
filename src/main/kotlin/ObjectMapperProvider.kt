import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object ObjectMapperProvider {

    val yamlMapper = createYamlMapper()
    val jsonMapper = createJsonMapper()

    private fun createYamlMapper(): ObjectMapper {
        return ObjectMapper(YAMLFactory()).registerKotlinModule()
    }

    private fun createJsonMapper(): ObjectMapper {
        val jsonMapper = ObjectMapper(JsonFactory()).registerKotlinModule()
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return jsonMapper
    }

}