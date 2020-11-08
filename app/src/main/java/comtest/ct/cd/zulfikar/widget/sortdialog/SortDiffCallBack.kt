package comtest.ct.cd.zulfikar.widget.sortdialog

import androidx.recyclerview.widget.DiffUtil

class SortDiffCallBack<T:SortLabel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getStringId() == newItem.getStringId()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getKeyId() == newItem.getKeyId()
    }
}
