import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.Locale

class HomeFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        
        // Replace deprecated calls with newer API
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // If these functions aren't being used, either remove them or mark them for future use
    @Suppress("unused")
    private fun getLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return prefs.getString("language_code", "en") ?: "en"
    }

    @Suppress("unused")
    private fun saveLanguageCode(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        prefs.edit().putString("language_code", languageCode).apply()
    }
} 