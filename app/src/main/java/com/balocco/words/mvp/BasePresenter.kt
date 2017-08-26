package com.balocco.words.mvp

interface BasePresenter<V> {

    fun setView(view: V)
    
}