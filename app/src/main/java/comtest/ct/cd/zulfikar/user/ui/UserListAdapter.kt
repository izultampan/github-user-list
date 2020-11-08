package comtest.ct.cd.zulfikar.user.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import comtest.ct.cd.zulfikar.R
import comtest.ct.cd.zulfikar.schema.Items

class UserListAdapter : ListAdapter<Items, UserListViewHolder>(UserListDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.v_user_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
