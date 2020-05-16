package com.major.beauty.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/4/3 22:45
 */
class BaseViewModel<S, F>(application: Application) :
    AndroidViewModel(application) {

    protected var mSuccess: MutableLiveData<S> = MutableLiveData()
    protected var mFailure: MutableLiveData<F> = MutableLiveData()
    protected var mLoading: MutableLiveData<Boolean?>? = MutableLiveData()

    fun onLoading(): MutableLiveData<Boolean?>? {
        return mLoading
    }

    fun onSuccess(): MutableLiveData<S> {
        return mSuccess
    }

    fun onFailure(): MutableLiveData<F> {
        return mFailure
    }
}