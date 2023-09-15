import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class HttpRequestManager {

  companion object {

    private const val REPOSITORY_URL = "https://plugins.jetbrains.com"
    const val API_URL = "$REPOSITORY_URL/api/plugins"
    const val DOWNLOAD_URL = "$REPOSITORY_URL/files"

    fun sendGetRequest(uri: URI): String {
      val client = HttpClient.newHttpClient()
      val request = HttpRequest.newBuilder()
        .uri(uri)
        .build()

      val response = client.send(request, HttpResponse.BodyHandlers.ofString())
      return response.body()
    }
  }

}
