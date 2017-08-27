package com.balocco.words.home.instructions.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import com.balocco.words.R
import com.balocco.words.common.ui.BaseFragment
import com.balocco.words.home.instructions.InstructionsContract
import com.balocco.words.home.instructions.presentation.InstructionsPresenter
import javax.inject.Inject

class InstructionsFragment : BaseFragment(),
        InstructionsContract.View {

    companion object {
        @JvmStatic fun newInstance(): Fragment = InstructionsFragment()
    }

    @Inject lateinit var presenter: InstructionsPresenter

    @BindView(R.id.btn_continue) lateinit var btnContinue: Button

    private lateinit var container: FragmentContainer

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = when (parentFragment) {
            null -> context as FragmentContainer
            else -> parentFragment as FragmentContainer
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)
        bindView(this, view)

        presenter.setView(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setContinueButtonEnabled(enabled: Boolean) {
        btnContinue.isEnabled = enabled
    }

    override fun showMessage(messageRes: Int) {
        container.showMessage(messageRes)
    }

    @OnClick(R.id.btn_continue)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_continue -> container.onInstructionsReady()
        }
    }

    interface FragmentContainer {

        fun onInstructionsReady()

        fun showMessage(@StringRes messageRes: Int)

    }

}