package com.balocco.words.home.translations.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
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
                translation.characterList,
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

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun startListeningTouchEvents() {
        rvTranslations.addOnItemTouchListener(recyclerViewTouchListener)
    }

    override fun stopListeningTouchEvents() {
        rvTranslations.removeOnItemTouchListener(recyclerViewTouchListener)
    }

    override fun setSelectedItems(positions: List<Int>) {
        charactersAdapter.setSelectedItems(positions)
    }

    interface FragmentContainer {

    }

}