package com.major.beauty.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * 2016年7月26日17:20:13
 *
 * @author 小瓶盖 blog    http://blog.csdn.net/qq_25193681/article/details/52005375
 */
class SecondSurfaceView : SurfaceView, SurfaceHolder.Callback, Runnable {
    /**
     * 是否处于绘制状态
     */
    private var mIsDrawing = false

    /**
     * 帮助类
     */
    private var mHolder: SurfaceHolder? = null

    /**
     * 画布
     */
    private var mCanvas: Canvas? = null

    /**
     * 路径
     */
    private val mPath: Path by lazy {
        Path()
    }

    /**
     * 画笔
     */
    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initView()
    }

    constructor(context: Context?) : super(context) {
        initView()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPath.moveTo(x.toFloat(), y.toFloat())
            MotionEvent.ACTION_MOVE -> mPath.lineTo(x.toFloat(), y.toFloat())
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        return true
    }

    private fun initView() {
        mHolder = holder
        mHolder?.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        this.keepScreenOn = true
    }

    override fun run() {
        val start = System.currentTimeMillis()
        while (mIsDrawing) {
            draw()
        }
        val end = System.currentTimeMillis()
        if (end - start < 100) {
            try {
                Thread.sleep(100 - (end - start))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun surfaceChanged(
        arg0: SurfaceHolder?,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun surfaceCreated(arg0: SurfaceHolder?) {
        mIsDrawing = true
        Thread(this).start()
    }

    override fun surfaceDestroyed(arg0: SurfaceHolder?) {
        mIsDrawing = false
    }

    private fun draw() {
        try {
            mCanvas = mHolder!!.lockCanvas()
            mCanvas?.run {
                drawColor(Color.WHITE)
                drawPath(mPath, mPaint)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (mCanvas != null) {
                mHolder!!.unlockCanvasAndPost(mCanvas)
            }
        }
    }

    /**
     * 清除内容
     */
    fun clean() {
        initView()
    }
}