package com.balocco.words.common.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.balocco.words.R

fun FragmentManager.replaceWithoutBackStack(layoutId: Int,
                                            fragment: Fragment,
                                            animationType: AnimationType) {
    val transaction = beginTransaction()
    if (animationType == AnimationType.SLIDE) {
        transaction.setCustomAnimations(
                R.anim.open_from_right,
                R.anim.close_to_left,
                R.anim.open_from_left,
                R.anim.close_to_right)
    }
    transaction.replace(layoutId, fragment)
    transaction.commit()
}