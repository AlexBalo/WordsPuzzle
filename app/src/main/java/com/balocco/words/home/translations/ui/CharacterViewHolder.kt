package com.balocco.words.home.translations.ui

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.words.R
import com.balocco.words.home.translations.CharacterItemContract
import com.balocco.words.home.translations.presentation.CharacterItemPresenter
import com.balocco.words.home.translations.usecases.CharacterTextSizeUseCase

class CharacterViewHolder(
        itemView: View,
        gridSize: Int,
        characterTextSizeUseCase: CharacterTextSizeUseCase
) : RecyclerView.ViewHolder(itemView), CharacterItemContract.View {

    @BindView(R.id.vg_character_cell) lateinit var vgCharacterCell: ViewGroup
    @BindView(R.id.v_selector) lateinit var vSelector: View
    @BindView(R.id.tv_character) lateinit var tvCharacter: TextView

    private val context: Context
    private val presenter: CharacterItemPresenter

    init {
        ButterKnife.bind(this, itemView)

        context = itemView.context

        presenter = CharacterItemPresenter(gridSize, characterTextSizeUseCase)
        presenter.setView(this)
        presenter.start()
    }

    fun onBindViewHolder(character: String,
                         position: Int,
                         selectedItems: List<Int>,
                         solutionItems: List<Int>) {
        presenter.onCharacterUpdated(character, position, selectedItems, solutionItems)
    }

    fun onViewRecycled() {
        presenter.destroy()
    }

    override fun setCharacterTextSize(@DimenRes textSize: Int) {
        tvCharacter.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(textSize))
    }

    override fun setCharacterText(text: String) {
        tvCharacter.text = text
    }

    override fun showSelector() {
        vSelector.visibility = View.VISIBLE
    }

    override fun hideSelector() {
        vSelector.visibility = View.GONE
    }

    override fun setCharacterTextColor(colorRes: Int) {
        tvCharacter.setTextColor(getColor(colorRes))
    }

    override fun setCellBackgroundColor(colorRes: Int) {
        vgCharacterCell.setBackgroundColor(getColor(colorRes))
    }

    @ColorInt
    private fun getColor(@ColorRes resource: Int): Int = ContextCompat.getColor(context, resource)

}