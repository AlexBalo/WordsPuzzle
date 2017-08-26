package com.balocco.words.home.translations

import android.view.MotionEvent
import com.balocco.words.mvp.BasePresenter
import com.balocco.words.mvp.BaseView

interface TranslationsContract {

    interface Presenter : BasePresenter<View> {

        fun resume()

        fun pause()

        fun onCharacterTouched(position: Int, eventAction: Int)

    }

    interface View : BaseView {

        fun startListeningTouchEvents()

        fun stopListeningTouchEvents()

        fun setSelectedItems(positions: List<Int>)

    }

}