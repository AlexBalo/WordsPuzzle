package com.balocco.words.home.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.words.R
import com.balocco.words.common.extensions.AnimationType
import com.balocco.words.common.extensions.replaceWithoutBackStack
import com.balocco.words.common.ui.BaseActivity
import com.balocco.words.data.models.Translation
import com.balocco.words.home.MainContract
import com.balocco.words.home.instructions.ui.InstructionsFragment
import com.balocco.words.home.presentation.MainPresenter
import com.balocco.words.home.result.ui.ResultFragment
import com.balocco.words.home.translations.ui.TranslationsFragment
import javax.inject.Inject

private const val ANIMATION_MULTIPLIER = 20
private const val PROGRESS_PROPERTY = "progress"
private const val PROGRESS_ANIMATION_DURATION_MS = 500L

class MainActivity : BaseActivity(),
        MainContract.View,
        InstructionsFragment.FragmentContainer,
        TranslationsFragment.FragmentContainer,
        ResultFragment.FragmentContainer {

    @Inject lateinit var presenter: MainPresenter

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.pb_progress_indicator) lateinit var pbProgressIndicator: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        presenter.setView(this)
        presenter.start(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.onSavedInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun showInstructions(animationType: AnimationType) {
        showFragment(InstructionsFragment.newInstance(), AnimationType.NONE)
    }

    override fun showTranslation(translation: Translation, animationType: AnimationType) {
        showFragment(TranslationsFragment.newInstance(translation), AnimationType.SLIDE)
    }

    override fun showResult(animationType: AnimationType) {
        showFragment(ResultFragment.newInstance(), AnimationType.NONE)
    }

    override fun updateProgress(maxValue: Int, currentValue: Int) {
        pbProgressIndicator.max = maxValue * ANIMATION_MULTIPLIER
        val currentProgress = pbProgressIndicator.progress
        val newProgress = currentValue * ANIMATION_MULTIPLIER
        val animator = ObjectAnimator.ofInt(
                pbProgressIndicator, PROGRESS_PROPERTY, currentProgress, newProgress)
        animator.duration = PROGRESS_ANIMATION_DURATION_MS
        animator.start()
    }

    override fun showMessage(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun onInstructionsReady() {
        presenter.onInstructionsReady()
    }

    override fun onGridCompleted() {
        presenter.onGridCompleted()
    }

    override fun onRestartRequested() {
        presenter.onRestartRequested()
    }

    override fun onFinishRequested() {
        presenter.onFinishRequested()
    }

    private fun showFragment(fragment: Fragment, animationType: AnimationType) =
            supportFragmentManager.replaceWithoutBackStack(
                    R.id.fragment_container, fragment, animationType)
}
