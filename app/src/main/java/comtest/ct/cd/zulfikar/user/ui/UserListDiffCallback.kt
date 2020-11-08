package comtest.ct.cd.zulfikar.user.ui

import androidx.recyclerview.widget.DiffUtil
import comtest.ct.cd.zulfikar.schema.Items

class UserListDiffCallback : DiffUtil.ItemCallback<Items>() {
    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.login == newItem.login
    }
}
