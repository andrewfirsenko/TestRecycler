package ru.cutepool.testrecycler.view

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.cutepool.testrecycler.adapters.ImageUserAdapter
import ru.cutepool.testrecycler.extensions.dpToPx
import ru.cutepool.testrecycler.model.Alien
import ru.cutepool.testrecycler.model.links

class UserImagesView : FrameLayout {
    private lateinit var storiesView: StoriesView
    private lateinit var imageList: RecyclerView
    private lateinit var imageUserAdapter: ImageUserAdapter

    private var recycledViewPool: RecyclerView.RecycledViewPool? = null
    private lateinit var glide: RequestManager
    private var pauseListener: StoriesView.PauseListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (!this::glide.isInitialized) {
            glide = Glide.with(this)
        }

        layoutTransition = LayoutTransition()
        //init StoriesView
        storiesView = StoriesView(context, attrs)
        storiesView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(3)).apply {
            setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(0))
        }

        //init RecyclerView
        imageUserAdapter = ImageUserAdapter(glide)
        val imageUserLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val imageUserSnapHelper: SnapHelper = PagerSnapHelper()

        imageList = RecyclerView(context, attrs).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutManager = imageUserLayoutManager
            adapter = imageUserAdapter
            setHasFixedSize(true)
            imageUserSnapHelper.attachToRecyclerView(this)
        }
        if (recycledViewPool != null) {
            imageList.setRecycledViewPool(recycledViewPool)
        }



        addView(imageList)
        addView(storiesView)

        //Set clickable view
        val linearLayout = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.HORIZONTAL
        }

        val leftView = View(context)
        val rightView = View(context)
        linearLayout.addView(leftView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f))
        linearLayout.addView(rightView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f))

        leftView.setOnTouchListener(storiesView.onPauseTouchListener)
        leftView.setOnClickListener(object : OnDoubleClickListener() {
            override fun onSingleClick(v: View) {
                storiesView.prev()
            }

            override fun onDoubleClick(v: View) {

            }
        })

        rightView.setOnTouchListener(storiesView.onPauseTouchListener)
        rightView.setOnClickListener(object : OnDoubleClickListener() {
            override fun onSingleClick(v: View) {
                storiesView.next()
            }

            override fun onDoubleClick(v: View) {

            }
        })

        addView(linearLayout)
    }

    /**
     * @param user - user that will be bind to this view.
     */
    public fun bindUser(user: Alien) {
        val images = user.links()
        imageUserAdapter.bindData(images)
        var current = 0
        val storiesListener = object : StoriesView.StoriesListener {
            override fun onNext() {
                imageList.smoothScrollToPosition(++current % imageUserAdapter.itemCount)
            }

            override fun onPrev() {
                if (current > 0) {
                    imageList.smoothScrollToPosition(--current % imageUserAdapter.itemCount)
                }
            }

            override fun onComplete() {
                current = 0
                imageList.smoothScrollToPosition(0)
            }
        }
        storiesView.apply {
            setCount(images.size)
            setStoriesListener(storiesListener)
            setPauseListener(object : StoriesView.PauseListener {
                override fun onHide() {
                    pauseListener?.onHide()
                    storiesView.visibility = View.INVISIBLE
                }

                override fun onShow() {
                    pauseListener?.onShow()
                    storiesView.visibility = View.VISIBLE
                }
            })
        }.start()
    }

    fun setRecycledViewPool(recycledViewPool: RecyclerView.RecycledViewPool) {
        this.recycledViewPool = recycledViewPool
        imageList.setRecycledViewPool(recycledViewPool)
    }


    fun setOnPauseListener(pauseListener: StoriesView.PauseListener) {
        this.pauseListener = pauseListener
    }
}