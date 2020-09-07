package ru.cutepool.testrecycler.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.cutepool.testrecycler.R
import kotlin.math.max


class StoriesView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    private var frontColor = DEFAULT_FRONT_COLOR
    private var backColor = DEFAULT_BACK_COLOR
    private var count = 0
    private var progress = 0
    private var space = 0
    private val step = 1
    private var isPause = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    interface StoriesListener {
        fun onNext()
        fun onPrev()
        fun onComplete()
    }

    interface PauseListener {
        fun onHide()
        fun onShow()
    }

    private var storiesListener: StoriesListener? = null
    private var pauseListener: PauseListener? = null
    private val timer: HoldTimer = HoldTimer(DEFAULT_HIDE_TIME, 10) { pauseListener?.onHide() }

    private var currentStoriesTime: Float = DEFAULT_STORIES_TIME
    private var delay: Long = (1000 / (100 / currentStoriesTime)).toLong()

    val onPauseTouchListener: OnTouchListener = object : OnTouchListener {
        var pressTime = 0L
        var limit = 300L
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressTime = System.currentTimeMillis()
                    pause()
                    timer.start()
                    return false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    resume()
                    pauseListener?.onShow()
                    timer.cancel()
                    return limit < now - pressTime
                }
                MotionEvent.ACTION_CANCEL -> {
                    pauseListener?.onShow()
                    timer.cancel()
                    return false
                }
            }
            return false
        }
    }

    init {
        attrs?.let {
            val typedArray = context?.obtainStyledAttributes(it, R.styleable.StoriesView)
            typedArray?.let {
                frontColor = typedArray.getColor(R.styleable.StoriesView_front_color, DEFAULT_FRONT_COLOR)
                backColor = typedArray.getColor(R.styleable.StoriesView_back_color, Color.parseColor("#8affffff"))
                space = typedArray.getColor(R.styleable.StoriesView_space, DEFAULT_SPACE)
            }
            typedArray?.recycle()
        }
    }


    private fun startFrom(n: Int) {
        if (count == 0) {
            return
        }
        if (n < 0 || n >= count) {
            throw IllegalArgumentException("Wrong number of story.")
        }
        isPause = false
        progress = n * 100
        invalidate()
    }

    fun start() {
        startFrom(0)
    }

    fun pause() {
        isPause = true
    }

    fun resume() {
        isPause = false
    }

    fun setCount(count: Int) {
        this.count = count
        progress = 0
    }

    fun next() {
        if (progress + 100 >= count * 100) {
            progress = 0
            storiesListener?.onComplete()
        } else {
            progress = ((progress + 100) / 100) * 100
            storiesListener?.onNext()
        }
        resume()
    }

    fun prev() {
        progress = max(0, ((progress - 100) / 100) * 100)
        resume()
        storiesListener?.onPrev()
    }

    fun setStoriesListener(storiesListener: StoriesListener) {
        this.storiesListener = storiesListener
    }

    fun setPauseListener(pauseListener: PauseListener) {
        this.pauseListener = pauseListener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val start = System.currentTimeMillis()
        update(canvas)
        postInvalidateDelayed(start + delay - System.currentTimeMillis())
    }

    private fun update(canvas: Canvas) {
        if (count == 0) {
            return
        }
        val rectWidth = (width - (space * count - 1)) / count
        var x = 0.0f
        val top = 0.0f
        val bot = height.toFloat()

        paint.color = backColor

        while (x < width) {
            canvas.drawRoundRect(x, top, x + rectWidth, bot, RADIUS, RADIUS, paint)
            x += rectWidth + space
        }

        x = 0.0f
        var curRect = 1
        paint.color = frontColor

        while (curRect * 100 < progress) {
            canvas.drawRoundRect(x, top, x + rectWidth, bot, RADIUS, RADIUS, paint)
            x += rectWidth + space
            curRect++
        }

        val lastActiveWidth = rectWidth - (curRect * 100 - progress) * rectWidth / 100
        canvas.drawRoundRect(x, top, x + lastActiveWidth, bot, RADIUS, RADIUS, paint)

        if (progress + step >= count * 100) {
            progress = 0
            storiesListener?.onComplete()
        }
        if (!isPause) {
            progress += step
            if (progress.div(100) != (progress - step).div(100)) {
                storiesListener?.onNext()
            }
        }
    }

    private class HoldTimer(
            millisInFuture: Long,
            countDownInterval: Long,
            private val onHide: () -> Unit
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            onHide()
        }

        override fun onTick(millisUtilFinished: Long) {

        }
    }

    companion object {
        private const val DEFAULT_FRONT_COLOR = Color.WHITE
        private const val DEFAULT_BACK_COLOR = Color.LTGRAY
        private const val DEFAULT_SPACE = 5
        private const val DEFAULT_STORIES_TIME = 5.0f
        private const val DEFAULT_HIDE_TIME = 1000L

        private const val RADIUS = 10.0f
    }
}