package comtest.ct.cd.zulfikar.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.quipper.common.mvi.MviView
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import comtest.ct.cd.zulfikar.R
import comtest.ct.cd.zulfikar.user.mvi.UserListIntent
import comtest.ct.cd.zulfikar.user.mvi.UserListViewState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_user_list.*
import timber.log.Timber

@AndroidEntryPoint
class UserListFragment : Fragment(), MviView<UserListIntent, UserListViewState> {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val viewModel: UserListViewModel by viewModels()

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    private val intentSubject = PublishSubject.create<UserListIntent>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processIntents(intents())

        txtSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let{
                        intentSubject.onNext(
                            UserListIntent.LoadUserListByNameIntent(it)
                        )
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe({
                render(it)
            }, {
                Timber.e(it)
            })
    }

    override fun intents(): Observable<UserListIntent> {
        return intentSubject.serialize()
    }

    override fun render(state: UserListViewState) {
        when (state) {
            is UserListViewState.ShowLoading -> {
                Timber.d("loading")
            }
            is UserListViewState.ShowUserList -> {
                state.userList.forEach {
                    Timber.d("izul : ${it.login}")
                }
            }
            is UserListViewState.ShowError -> {
                Timber.e(state.error)
            }
        }
    }
}
