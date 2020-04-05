package fr.m1miage.tmdb

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.m1miage.tmdb.utils.changeLanguage
import fr.m1miage.tmdb.utils.extension.getLocale
import kotlinx.android.synthetic.main.language_bottom_sheet.view.*
import java.util.*

class LanguagesBottomSheet(
    val pref: SharedPreferences,
    val ressources: Resources,
    val onClick: () -> Unit
) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.language_bottom_sheet, container, false)
        when (pref.getLocale()) {
            Locale.ENGLISH -> view.english_button.toggle()
            Locale("es") -> view.spanish_button.toggle()
            else -> view.french_button.toggle()
        }

        view.french_button.setOnClickListener { onButtonClick(Locale.FRANCE) }

        view.english_button.setOnClickListener { onButtonClick(Locale.ENGLISH) }

        view.spanish_button.setOnClickListener { onButtonClick(Locale("es")) }

        return view
    }

    private fun onButtonClick(locale: Locale) {
        changeLanguage(locale, pref, resources)
        onClick()
    }


}