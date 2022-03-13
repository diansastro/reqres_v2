package com.suitmedia.reqres_v2.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T: ErrorView> {

    var compose: CompositeDisposable = CompositeDisposable()

    var view:T ?= null

    open fun detachView(){
        this.view = null
        compose.clear()
    }

    fun addSubscription(disposable: Disposable) = compose.add(disposable)

    fun clearAllSubscription() = compose.clear()
}