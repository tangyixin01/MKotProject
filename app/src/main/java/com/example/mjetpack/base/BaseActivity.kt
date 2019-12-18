package com.example.mjetpack.base

import android.animation.Animator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.animation.addListener
import butterknife.ButterKnife
import com.example.mjetpack.R
import com.example.mjetpack.util.StatusBarUtil
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.common_activity_layout.*

abstract class BaseActivity : AppCompatActivity() {

    private var avLoadingIndicatorView:AVLoadingIndicatorView?=null

    companion object {
        //手动往 intent 里传入上个界面的点击位置坐标
        val CLICK_X = "CLICK_X"
        val CLICK_Y = "CLICK_Y"
    }
    //揭露(进入)动画
    var mAnimReveal : Animator? = null
    //反揭露(退出)动画
    var mAnimRevealR : Animator? = null

    private var onGlobalLayout : ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_activity_layout)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        val childView = layoutInflater.inflate(getView(),null)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        content_view.addView(childView,lp)
        StatusBarUtil.immersive(this)
        ButterKnife.bind(this)
        initView()
        initData()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

    }

    abstract fun getView():Int
    abstract fun initView()
    abstract fun initData()

    fun  startScaleSAnim(view: View){
        view.animate().scaleX(0.8f).scaleY(0.8f).setDuration(1000).start()
    }
    fun  startScaleBAnim(view: View){
        view.animate().scaleX(1f).scaleY(1f).setDuration(1000).start()
    }
    fun circularReveal (intent: Intent){
        //系统提供的揭露动画需 5.0 及以上的 sdk 版本，当我们获取不到上个界面的点击区域时就不展示揭露动画，因为此时没有合适的锚点
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ||
            (intent?.sourceBounds == null && intent?.hasExtra(CLICK_X)?.not()?:true)) return

        val rect = intent?.sourceBounds
        val v = window.decorView
        v.visibility = View.INVISIBLE

        @SuppressWarnings
        onGlobalLayout = object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {//此时既是开始揭露动画的最佳时机
                mAnimReveal?.removeAllListeners()
                mAnimReveal?.cancel()
                mAnimReveal = ViewAnimationUtils.createCircularReveal(v,
                    rect?.centerX()?:intent?.getIntExtra(CLICK_X, 0)?:0,
                    rect?.centerY()?:intent?.getIntExtra(CLICK_Y, 0)?:0,
                    0f,
                    v.height.toFloat()
                )
                mAnimReveal?.duration = 400
                mAnimReveal?.interpolator = LinearInterpolator()
                mAnimReveal?.addListener(onEnd = {
                    onGlobalLayout?.let {
                        //我们需要在揭露动画进行完后及时移除回调
                        v?.viewTreeObserver?.removeOnGlobalLayoutListener(it)
                    }
                })
                mAnimReveal?.start()
            }
        }
        //视图可见性发生变化时的回调，回调里正是开始揭露动画的最佳时机
        v.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayout)
    }
    //Activtiy 反揭露(退出)动画，即退出时的过渡动画，
    //这么起名可能不恰当，其实还是同样的动画，
    //只不过揭露的起始和终结半径跟上面相比反过来了
    fun circularRevealReverse(intent: Intent?){
        //系统提供的揭露动画需 5.0 及以上的 sdk 版本，当我们获取不到上个界面的点击区域时就不展示揭露动画，因为此时没有合适的锚点
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ||
            (intent?.sourceBounds == null && intent?.hasExtra(CLICK_X)?.not()?:true)) {
            super.onBackPressed()
            return
        }
        val rect = intent?.sourceBounds
        val v = window.decorView
        mAnimRevealR?.removeAllListeners()
        mAnimRevealR?.cancel()
        mAnimRevealR = ViewAnimationUtils.createCircularReveal(v,
            rect?.centerX()?:intent?.getIntExtra(CLICK_X, 0)?:0,
            rect?.centerY()?:intent?.getIntExtra(CLICK_Y, 0)?:0,
            v.height.toFloat(),
            0f

        )
        mAnimRevealR?.duration = 400
        mAnimRevealR?.interpolator = LinearInterpolator()
        mAnimRevealR?.addListener(onEnd = {
            v.visibility = View.GONE
            super.onBackPressed()
        })
        mAnimRevealR?.start()
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
}
