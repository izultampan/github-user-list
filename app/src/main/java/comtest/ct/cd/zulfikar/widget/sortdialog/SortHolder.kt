package comtest.ct.cd.zulfikar.widget.sortdialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_sort.view.*

class SortHolder<T : SortLabel>(private val view: View, private val listener: PublishSubject<T>): RecyclerView.ViewHolder(view) {

    fun bind(label: T) {
        view.txtSortLabel.text = view.context.getString(label.getStringId())
        view.setOnClickListener {
            listener.onNext(label)
        }
    }
}
