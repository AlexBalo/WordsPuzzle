package com.balocco.words.home.translations.presentation

import com.balocco.words.R
import com.balocco.words.home.translations.CharacterItemContract
import com.balocco.words.home.translations.usecases.CharacterTextSizeUseCase
import com.balocco.words.mvp.ReactivePresenter

class CharacterItemPresenter(
        private val gridSize: Int,
        private val characterTextSizeUseCase: CharacterTextSizeUseCase
) : ReactivePresenter(), CharacterItemContract.Presenter {

    private lateinit var view: CharacterItemContract.View

    private var characterPosition = 0

    override fun setView(view: CharacterItemContract.View) {
        this.view = view
    }

    override fun start() {
        view.setCharacterTextSize(characterTextSizeUseCase.getTextSizeBasedOnGrid(gridSize))
    }

    override fun onCharacterUpdated(character: String,
                                    position: Int,
                                    selectedItems: List<Int>,
                                    solutionItems: List<Int>) {
        characterPosition = position

        view.setCharacterText(character)

        if (selectedItems.contains(position)) {
            view.showSelector()
            view.setCharacterTextColor(android.R.color.white)
        } else {
            view.hideSelector()
            view.setCharacterTextColor(android.R.color.black)
        }

        if (solutionItems.contains(position)) {
            view.setCellBackgroundColor(R.color.colorSolution)
        } else {
            view.setCellBackgroundColor(android.R.color.transparent)
        }
    }

}