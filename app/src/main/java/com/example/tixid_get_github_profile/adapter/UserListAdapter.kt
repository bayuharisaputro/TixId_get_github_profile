package com.example.tixid_get_github_profile.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tixid_get_github_profile.R
import com.example.tixid_get_github_profile.model.UserListResponse
import kotlinx.android.synthetic.main.item_user_list.view.*


class UserListAdapter(var listData: ArrayList<UserListResponse?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemAction {
        fun onGetDetailUser(userId : String)
    }

    var itemActionListener: OnItemAction? = null

    companion object {
        const val TYPE_LOADING = 0
        const val TYPE_CONTENT = 1
    }

    override fun getItemCount(): Int = listData.size

    override fun getItemId(position: Int): Long {
        val data: UserListResponse = listData[position]?:return 0
        return data.id.toLong()
    }

    override fun getItemViewType(position: Int): Int = if(listData[position] == null) TYPE_LOADING else TYPE_CONTENT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                HolderLoading(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
            }
            else -> {
                HolderContent(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false))
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BindableHolder)?.bind(position)
    }

    inner class HolderContent(itemView: View) : RecyclerView.ViewHolder(itemView), BindableHolder {
        private val mImageViewUser = itemView.iv_pp_user
        private val mIdUser = itemView.tv_id
        private val mUsername= itemView.tv_username
        private val mProgressBar= itemView.progressBar

        init {
            itemView.setOnClickListener {
                if(adapterPosition != -1) {
                    itemActionListener?.onGetDetailUser(listData[adapterPosition]?.id.toString())
                }
            }
        }

        override fun bind(pos: Int) {
            val data = listData[pos]?:return
            if(data.isLoading) {
                mProgressBar.visibility = View.VISIBLE
            }
            else {
                mProgressBar.visibility = View.GONE
            }
            mIdUser.text = data.id.toString()
            mUsername.text = data.login
            Glide.with(itemView.context)
                .load(data.avatar_url)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(mImageViewUser)
        }
    }

    inner class HolderLoading(itemView: View) : RecyclerView.ViewHolder(itemView), BindableHolder {

        override fun bind(pos: Int) {
        }
    }
}