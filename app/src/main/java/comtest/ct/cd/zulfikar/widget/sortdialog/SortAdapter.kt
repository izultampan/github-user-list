package comtest.ct.cd.zulfikar.widget.sortdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import comtest.ct.cd.zulfikar.R
import io.reactivex.subjects.PublishSubject

class SortAdapter<T : SortLabel> : ListAdapter<T, SortHolder<T>>(SortDiffCallBack<T>()) {

    val listener: PublishSubject<T> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortHolder<T> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sort, parent, false)
        return SortHolder(view, listener)
    }

    override fun onBindViewHolder(holder: SortHolder<T>, position: Int) {
        val value = getItem(position)
        holder.bind(value)
    }
}
