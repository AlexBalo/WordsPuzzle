package com.balocco.words.home.translations.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.balocco.words.R
import com.balocco.words.common.ui.BaseFragment
import com.balocco.words.common.usecases.ScreenSizeUseCase
import com.balocco.words.data.models.Translation
import com.balocco.words.home.translations.RecyclerTouchListener
import com.balocco.words.home.translations.TranslationsContract
import com.balocco.words.home.translations.presentation.TranslationsPresenter
import com.balocco.words.home.translations.usecases.CoordinatesArrayUseCase
import com.balocco.words.home.translations.usecases.CoordinatesComparatorUseCase
import com.balocco.words.home.translations.usecases.PositionCoordinateUseCase
import javax.inject.Inject

private const val NO_TRANSLATION_Y: Float = 0f
private const val TRANSLATION_Y: Float = 200f

class TranslationsFragment : BaseFragment(),
        TranslationsContract.View {

    companion object {
        private const val KEY_TRANSLATION = "KEY_TRANSLATION"

        @JvmStatic fun newInstance(translation: Translation): Fragment =
                TranslationsFragment().apply {
                    val args = Bundle()
                    args.putParcelable(KEY_TRANSLATION, translation)
                    arguments = args
                }
    }

    @Inject lateinit var screenSizeUseCase: ScreenSizeUseCase
    @Inject lateinit var positionCoordinateUseCase: PositionCoordinateUseCase
    @Inject lateinit var coordinatesArrayUseCase: CoordinatesArrayUseCase
    @Inject lateinit var coordinateComparatorUseCase: CoordinatesComparatorUseCase

    @BindView(R.id.tv_source_word) lateinit var tvSourceWord: TextView
    @BindView(R.id.rv_translations) lateinit var rvTranslations: RecyclerView
    @BindView(R.id.btn_next) lateinit var btnNext: Button

    private lateinit var container: FragmentContainer
    private lateinit var presenter: TranslationsPresenter
    private lateinit var translation: Translation
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var recyclerViewTouchListener: RecyclerTouchListener

    private val itemTouchListener = object : RecyclerTouchListener.ItemTouchListener {
        override fun onItemTouched(position: Int, eventAction: Int) {
            presenter.onCharacterTouched(position, eventAction)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = when (parentFragment) {
            null -> context as FragmentContainer
            else -> parentFragment as FragmentContainer
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        translation = arguments.getParcelable(KEY_TRANSLATION)

        recyclerViewTouchListener = RecyclerTouchListener(itemTouchListener)

        charactersAdapter = CharactersAdapter(
                activity,
                translation.gridSize,
                screenSizeUseCase)

        presenter = TranslationsPresenter(
                translation,
                positionCoordinateUseCase,
                coordinatesArrayUseCase,
                coordinateComparatorUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_translations, container, false)
        bindView(this, view)

        tvSourceWord.text = translation.word

        val columnCount = translation.gridSize

        rvTranslations.apply {
            layoutManager = GridLayoutManager(activity, columnCount)
            adapter = charactersAdapter
            setHasFixedSize(true)
        }

        presenter.setView(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.onSavedInstanceState(outState)
    }

    @OnClick(R.id.btn_next)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_next -> container.onGridCompleted()
        }
    }

    override fun startListeningTouchEvents() {
        rvTranslations.addOnItemTouchListener(recyclerViewTouchListener)
    }

    override fun stopListeningTouchEvents() {
        rvTranslations.removeOnItemTouchListener(recyclerViewTouchListener)
    }

    override fun setCharacters(characters: List<String>) {
        charactersAdapter.setCharacters(characters)
    }

    override fun setSelectedItems(positions: List<Int>) {
        charactersAdapter.setSelectedItems(positions)
    }

    override fun setSolutionItems(positions: List<Int>) {
        charactersAdapter.setSolutionItems(positions)
    }

    override fun showNextButton() {
        btnNext.visibility = View.VISIBLE
        btnNext.translationY = TRANSLATION_Y
        btnNext.animate().translationY(NO_TRANSLATION_Y)
    }

    interface FragmentContainer {

        fun onGridCompleted()

    }

}