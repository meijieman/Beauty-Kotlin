package com.major.beauty.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.base
 * ProjectName: Beauty
 * Date: 2019/6/3 12:42
 */
abstract class BaseFragment : Fragment() {

    protected var mActivity: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(rootView, container, false)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    protected abstract val rootView: Int

    protected abstract fun init()

    protected fun skipIntent(cls: Class<*>?) {
        startActivity(Intent(context, cls))
    }
}