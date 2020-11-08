package comtest.ct.cd.zulfikar.widget.sortdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import comtest.ct.cd.zulfikar.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_sort.*

class SortDialog<T : SortLabel> : DialogFragment() {

   private val adapter: SortAdapter<T> = SortAdapter()

    companion object {
        const val IS_CANCELLABLE = "isCancellable"
        const val TITLE = "title"

        fun <T : SortLabel> createInstance(isCancellable: Boolean, title: String): SortDialog<T> {
            return SortDialog<T>().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_CANCELLABLE, isCancellable)
                    putString(TITLE, title)
                }
            }
        }

        fun <T : SortLabel> createInstance(isCancellable: Boolean): SortDialog<T> {
            return SortDialog<T>().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_CANCELLABLE, isCancellable)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(
            requireContext()
        )
        list.adapter = adapter

        isCancelable = arguments?.getBoolean(IS_CANCELLABLE) ?: false
        title.text = arguments?.getString(TITLE)
    }

    fun submitList(list: List<T>) {
        adapter.submitList(list)
    }

    fun getListener(): Observable<T> {
        return adapter.listener
    }

}
