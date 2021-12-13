package com.example.tixid_get_github_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tixid_get_github_profile.adapter.UserListAdapter
import com.example.tixid_get_github_profile.dependencyInjection.component.BaseApp
import com.example.tixid_get_github_profile.dialog.ErrorDialog
import com.example.tixid_get_github_profile.exception.ApiResponseException
import com.example.tixid_get_github_profile.exception.NoInternetException
import com.example.tixid_get_github_profile.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: UserListAdapter
    val loadPaginThreshold = 2
    var firstLoadID = 10000
    private var mErrorDialog: ErrorDialog? = null
    @Inject
    lateinit var userListViewModelFactory: ViewModelProvider.Factory
    private val userListVM by viewModels<UserListViewModel> { userListViewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as BaseApp).appComponent.userListTaskComponent().create().inject(this)

        initView()
        initData()
        initEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mErrorDialog?.isShowing == true) {
            mErrorDialog?.dismiss()
        }
    }
    private fun initData() {
        userListVM.requestGetUserList(firstLoadID)
    }


    private fun initView() {
        mAdapter = UserListAdapter(arrayListOf())
        mAdapter.itemActionListener = object : UserListAdapter.OnItemAction {
            override fun onGetDetailUser(userId: String) {
                mAdapter.listData.find { it?.id == userId.toInt()  }?.isLoading = true
                mAdapter.notifyDataSetChanged()
                userListVM.requestGetUserDetail(userId)
            }

        }
        mAdapter.setHasStableIds(true)

        mErrorDialog = ErrorDialog(this)

        rv_data.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
            itemAnimator = null
        }
    }


    private fun initEvent() {
        rv_data.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (userListVM.ldIsLoadingMore.value == true) return

                (rv_data.layoutManager as? LinearLayoutManager)?.let {
                    val lastPos = it.findLastVisibleItemPosition()
                    if (mAdapter.listData.size - lastPos < loadPaginThreshold ) {
                        if(mAdapter.itemCount > 0) {
                            val lastId = (mAdapter.listData[mAdapter.itemCount - 1]?.id ?: 0)
                            mAdapter.listData.add(null)
                            mAdapter.notifyDataSetChanged()
                            userListVM.requestLoadMoreUserList(lastId)
                        }
                    }
                }
            }
        })


        sr_load.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            userListVM.requestGetUserList(firstLoadID)
        })

        userListVM.ldIsLoading.observe(this, {
            if(it) {
                if(!sr_load.isRefreshing) pb_load_data.visibility = View.VISIBLE
                if(pb_load_data.visibility == View.GONE) sr_load.isRefreshing = true

            }
            else {
                if (sr_load.isRefreshing) sr_load.isRefreshing = false
                pb_load_data.visibility = View.GONE
            }
        })

        userListVM.ldUserList.observe(this, {
            if(!it.isNullOrEmpty()) {
                iv_error_no_msg.visibility = View.GONE
                tv_no_data.visibility = View.GONE
                mAdapter.listData = it
                mAdapter.notifyDataSetChanged()
            }
            else {
                if (mAdapter.listData.isNullOrEmpty()) {
                    iv_error_no_msg.visibility = View.VISIBLE
                    tv_no_data.visibility = View.VISIBLE
                }
            }
        })

        userListVM.ldUserDetail.observe(this, {
            if(it != null) {
                val email = it.email?:" -"
                val location = it.location?:" -"
                Toast.makeText(this, "id : ${it.id} " +
                        "\n username : ${it.login}" +
                        "\n email : ${email}" +
                        "\n location : ${location}" +
                        "\n createdAt : ${it.created_at}" , Toast.LENGTH_LONG).show()
            }
        })

        userListVM.ldIsLoadingMore.observe(this, {
            if(!it) {
                mAdapter.listData.remove(null)
                mAdapter.notifyDataSetChanged()
            }
        })

        userListVM.ldIsLoadingGetDetail.observe(this, {
            if(!it) {
                mAdapter.listData.find { it?.isLoading == true }?.isLoading = false
                mAdapter.notifyDataSetChanged()
            }
        })

        userListVM.ldErrorHandler.observe(this, {
            if(it!=null) {
                if(it is ApiResponseException) {
                    if(it.errorCode == "401"  || it.errorCode == "403") {
                        mErrorDialog?.show()
                        mErrorDialog?.setDialogData(it.errorCode, it.errorMessage?:"")
                    }
                    else {
                        Toast.makeText(this, "error code : ${it.errorCode}  \n error message : ${
                            it.localizedMessage
                        }", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    if(it is NoInternetException) {
                        Toast.makeText(this, "There is no internet" , Toast.LENGTH_LONG).show()

                    }
                    else {
                        Toast.makeText(this, "Ooops, something when wrong ;(" , Toast.LENGTH_LONG).show()
                    }
                }

            }
        })
    }
}