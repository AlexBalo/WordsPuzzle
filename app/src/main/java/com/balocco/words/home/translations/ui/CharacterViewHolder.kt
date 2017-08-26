package com.balocco.words.home.translations.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.words.R

class CharacterViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.v_selector) lateinit var vSelector: View
    @BindView(R.id.tv_character) lateinit var tvCharacter: TextView

    private val context: Context
    private var characterPosition = 0

    init {
        ButterKnife.bind(this, itemView)

        context = itemView.context
    }

    fun onBindViewHolder(character: String,
                         position: Int,
                         selectedItems: List<Int>) {
        characterPosition = position

        tvCharacter.text = character
        if (selectedItems.contains(position)) {
            vSelector.visibility = View.VISIBLE
            tvCharacter.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        } else {
            vSelector.visibility = View.GONE
            tvCharacter.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }
    }

}