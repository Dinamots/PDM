package fr.m1miage.tmdb.listeners.button

import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.CompoundButton

class FavoriteButtonCheckChangeListener : CompoundButton.OnCheckedChangeListener {

    companion object {
        private fun getScaleAnimation(): ScaleAnimation {
            val scaleAnimation = ScaleAnimation(
                0.7f,
                1.0f,
                0.7f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.7f,
                Animation.RELATIVE_TO_SELF,
                0.7f
            )
            scaleAnimation.duration = 500
            val bounceInterpolator = BounceInterpolator()
            scaleAnimation.interpolator = bounceInterpolator
            return scaleAnimation
        }

    }
    override fun onCheckedChanged(button: CompoundButton?, p1: Boolean) {
        button?.startAnimation(getScaleAnimation())
    }
}