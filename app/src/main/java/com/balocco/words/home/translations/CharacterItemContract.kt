package com.balocco.words.home.translations

import android.support.annotation.ColorRes
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface CharacterItemContract {

    interface Presenter : BasePresenter<View> {

        fun onCharacterUpdated(character: String,
                               position: Int,
                               selectedItems: List<Int>,
                               solutionItems: List<Int>)

    }

    interface View : BaseView {

        fun setCharacterText(text: String)

        fun setCharacterTextColor(@ColorRes colorRes: Int)

        fun showSelector()

        fun hideSelector()

        fun setCellBackgroundColor(@ColorRes colorRes: Int)

    }

}