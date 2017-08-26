package com.balocco.words.home.translations.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.balocco.words.R
import com.balocco.words.common.usecases.ScreenSizeUseCase

class CharactersAdapter(
        context: Context,
        private val gridSize: Int,
        private val characters: List<String>,
        private val screenSizeUseCase: ScreenSizeUseCase
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val selectedItems = arrayListOf<Int>()
    private val solutionItems = arrayListOf<Int>()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.listitem_character, parent, false)
        setViewLayoutParams(view)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]
        val characterViewHolder = viewHolder as CharacterViewHolder
        characterViewHolder.onBindViewHolder(
                character,
                position,
                selectedItems,
                solutionItems)
    }

    fun setSelectedItems(positions: List<Int>) {
        selectedItems.clear()
        selectedItems.addAll(positions)
        notifyDataSetChanged()
    }

    fun setSolutionItems(positions: List<Int>) {
        solutionItems.clear()
        solutionItems.addAll(positions)
        notifyDataSetChanged()
    }

    private fun setViewLayoutParams(view: View) {
        val width = screenSizeUseCase.getScreenWidth() / gridSize
        val params = FrameLayout.LayoutParams(width, width)
        view.layoutParams = params
    }

}
