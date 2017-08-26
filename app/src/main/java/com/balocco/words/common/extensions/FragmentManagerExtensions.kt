package com.balocco.words.common.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.replaceWithoutBackStack(layoutId: Int, fragment: Fragment) {
    val transaction = beginTransaction()
    transaction.replace(layoutId, fragment)
    transaction.commit()
}