package com.balocco.words.home.translations.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.balocco.words.R
import com.balocco.words.common.usecases.ScreenSizeUseCase
import com.balocco.words.home.translations.usecases.CharacterTextSizeUseCase

class CharactersAdapter(
        context: Context,
        private val gridSize: Int,
        screenSizeUseCase: ScreenSizeUseCase,
        private val characterTextUseCase: CharacterTextSizeUseCase
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val characters = arrayListOf<String>()
    private val selectedItems = arrayListOf<Int>()
    private val solutionItems = arrayListOf<Int>()
    private val cellSize: Int

    init {
        val margin = context.resources.getDimensionPixelSize(R.dimen.character_grid_margin)
        val width = screenSizeUseCase.getScreenWidth() - (margin * 2)
        cellSize = width / gridSize
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = characters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.listitem_character, parent, false)
        setViewLayoutParams(view)
        return CharacterViewHolder(view, gridSize, characterTextUseCase)
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

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        if (holder is CharacterViewHolder) {
            holder.onViewRecycled()
        }
    }

    fun setCharacters(charactersList: List<String>) {
        characters.clear()
        characters.addAll(charactersList)
        notifyDataSetChanged()
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
        val params = FrameLayout.LayoutParams(cellSize, cellSize)
        view.layoutParams = params
    }

}
