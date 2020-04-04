package fr.m1miage.tmdb

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.m1miage.tmdb.utils.extension.setLocale
import fr.m1miage.tmdb.utils.extension.getLocale
import kotlinx.android.synthetic.main.language_bottom_sheet.view.*
import java.util.*

class LanguagesBottomSheet(val pref: SharedPreferences,val ressources: Resources, val onClick: () -> Unit) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.language_bottom_sheet, container, false)
        when (pref.getLocale()) {
            Locale.ENGLISH -> view.english_button.toggle()
            else -> view.french_button.toggle()
        }
        view.french_button.setOnClickListener {
            changeLanguage(Locale.FRANCE)
            onClick()
        }
        view.english_button.setOnClickListener {
            changeLanguage(Locale.ENGLISH)
            onClick()
        }
        return view
    }

    private fun changeLanguage(locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            pref.setLocale(locale)
            val res: Resources = resources
            val dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
            conf.setLocale(locale)
            res.updateConfiguration(conf, dm)
        }
    }


}