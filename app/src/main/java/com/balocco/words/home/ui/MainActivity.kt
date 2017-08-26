package com.balocco.words.home.ui

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.words.R
import com.balocco.words.common.extensions.replaceWithoutBackStack
import com.balocco.words.common.ui.BaseActivity
import com.balocco.words.data.models.Translation
import com.balocco.words.home.MainContract
import com.balocco.words.home.instructions.ui.InstructionsFragment
import com.balocco.words.home.presentation.MainPresenter
import com.balocco.words.home.translations.ui.TranslationsFragment
import javax.inject.Inject

class MainActivity : BaseActivity(),
        MainContract.View,
        InstructionsFragment.FragmentContainer,
        TranslationsFragment.FragmentContainer {

    @Inject lateinit var presenter: MainPresenter

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        presenter.setView(this)
        presenter.start(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun showInstructions() {
        showFragment(InstructionsFragment.newInstance())
    }

    override fun showTranslation(translation: Translation) {
        showFragment(TranslationsFragment.newInstance(translation))
    }

    override fun onInstructionsReady() {
        presenter.onInstructionsReady()
    }

    override fun showMessage(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    private fun showFragment(fragment: Fragment) =
            supportFragmentManager.replaceWithoutBackStack(R.id.fragment_container, fragment)
}
