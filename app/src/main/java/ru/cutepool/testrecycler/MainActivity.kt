package ru.cutepool.testrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import ru.cutepool.testrecycler.adapters.MainAdapter
import ru.cutepool.testrecycler.model.Alien
import ru.cutepool.testrecycler.view.StoriesView

const val PAGINATION_THRESHOLD = 10

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var helper: SnapHelper

    private var showingUser: Alien? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = MainAdapter(object : StoriesView.PauseListener {
            override fun onHide() {

            }

            override fun onShow() {

            }
        })

        adapter.update(GenerateAlien.generate(30))

//        adapter.setHasStableIds(true)
//        helper = PagerSnapHelper()
//        helper.attachToRecyclerView(act_main__rv)


        act_main__rv.adapter = adapter
        act_main__rv.layoutManager = layoutManager
//        act_main__rv.setHasFixedSize(true)
//        act_main__rv.setItemViewCacheSize(10)
//        act_main__rv.clearOnScrollListeners()



//        act_main__rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val pos = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                if (pos + PAGINATION_THRESHOLD > adapter.itemCount) {
//                    Log.d("DEBUG", "want loadUsers")
//                }
//            }
//        })
//        act_main__rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            var lastPosition = 0
//            var maxPosition = 0
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val snapView = helper.findSnapView(layoutManager)
//                if (snapView != null) {
//                    val pos = layoutManager.getPosition(snapView)
//                    updateUser(adapter.getUser(pos))
//                    if (pos == lastPosition) {
//                        return
//                    }
//                    if (pos > maxPosition) {
//                        maxPosition = pos
//                    }
//                    lastPosition = pos
//                }
//            }
//        })
    }

    private fun updateUser(user: Alien) {
        if (user.id == showingUser?.id) {
            return
        }
        showingUser = user
    }
}