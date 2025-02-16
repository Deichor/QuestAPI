import java.io.File
import java.util.Properties

object EnvLoader {
    private val properties = Properties()

    init {
        try {
            // Önce proje dizininde .env dosyası var mı kontrol et
            val envFile = File(".env")
            if (envFile.exists()) {
                envFile.inputStream().use { stream ->
                    properties.load(stream)
                }
            }
        } catch (e: Exception) {
            println("Warning: .env file could not be loaded: ${e.message}")
        }
    }

    fun get(key: String): String? {
        // Önce system environment'dan kontrol et
        return System.getenv(key)
        // Yoksa .env dosyasından kontrol et
            ?: properties.getProperty(key)
    }

    fun getOrDefault(key: String, defaultValue: String): String {
        return get(key) ?: defaultValue
    }

    fun getRequired(key: String): String {
        return get(key) ?: throw IllegalStateException("Required environment variable $key not found")
    }

    fun getAllProperties(): Map<String, String> {
        return properties.stringPropertyNames()
            .associateWith { properties.getProperty(it) }
    }
}