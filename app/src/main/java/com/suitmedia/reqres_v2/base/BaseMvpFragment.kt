package com.suitmedia.reqres_v2.base

abstract class BaseMvpFragment<T: BasePresenter<*>>: BaseFragment() {
    protected abstract var presenter: T

    override fun internalSetup() {
        initPresenterView()
        super.internalSetup()
    }

    abstract fun initPresenterView()

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}