package comtest.ct.cd.zulfikar.user.ui

import androidx.recyclerview.widget.DiffUtil
import comtest.ct.cd.zulfikar.schema.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login
    }
}
