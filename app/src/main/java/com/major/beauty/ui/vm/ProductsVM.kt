package com.major.beauty.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/8 18:20
 */
class ProductsVM : ViewModel() {
    val update: MutableLiveData<Int>
        get() = mUpdate

    companion object {
        const val ADD = 1
        const val DEL = 2
        private val mUpdate: MutableLiveData<Int> = MutableLiveData()
    }
}