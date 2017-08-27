package com.balocco.words.home.result.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.balocco.words.R
import com.balocco.words.common.ui.BaseFragment
import com.balocco.words.home.result.ResultContract
import com.balocco.words.home.result.presentation.ResultPresenter
import javax.inject.Inject

class ResultFragment : BaseFragment(),
        ResultContract.View {

    companion object {
        @JvmStatic fun newInstance(): Fragment = ResultFragment()
    }

    @Inject lateinit var presenter: ResultPresenter

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
        val view = inflater.inflate(R.layout.fragment_result, container, false)
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

    @OnClick(R.id.btn_restart, R.id.btn_finish)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_restart -> container.onRestartRequested()
            R.id.btn_finish -> container.onFinishRequested()
        }
    }

    interface FragmentContainer {

        fun onRestartRequested()

        fun onFinishRequested()

    }

}