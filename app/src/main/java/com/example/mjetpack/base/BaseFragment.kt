package com.example.mjetpack.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.mjetpack.R
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.common_activity_layout.*

/**
 * author:chenjian
 * date: 2019-12-11   14:58
 **/
 abstract class BaseFragment : Fragment() {
    /**
     * loading
     */
    private var avLoadingIndicatorView: AVLoadingIndicatorView?=null

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),container,false)

    }

    fun  startScaleSAnim(view: View) {
        view.animate().scaleX(0.8f).scaleY(0.8f).setDuration(100).start()

    }
    fun  startScaleBAnim(view: View){
        view.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
    }

    fun goFragmentAnim() : NavOptions{

        return  navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
     }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
    }

    private fun lazyLoadDataIfPrepared() {
        if (isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    fun showLoading(){

        avLoadingIndicatorView?.let {
            it.show()
        }?:createLoadingView()
    }
    fun cacelLoading(){
        avLoadingIndicatorView?.let {
            it.hide()
        }
    }
    private fun createLoadingView(){
        val lp = FrameLayout.LayoutParams(200,
            200)
        lp.gravity = Gravity.CENTER
        avLoadingIndicatorView = AVLoadingIndicatorView(BaseContext.getApplication())

        content_view.addView(avLoadingIndicatorView?.apply {

            setIndicatorColor(R.color.appColor)
            setIndicator("BallClipRotateMultipleIndicator")

        },lp)



    }

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId():Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()
}