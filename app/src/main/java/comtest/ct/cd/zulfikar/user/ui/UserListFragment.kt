package comtest.ct.cd.zulfikar.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.quipper.common.mvi.MviView
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import comtest.ct.cd.zulfikar.R
import comtest.ct.cd.zulfikar.ext.createSnackBar
import comtest.ct.cd.zulfikar.ext.createSnackBarNoAction
import comtest.ct.cd.zulfikar.user.UserListOrderBy
import comtest.ct.cd.zulfikar.user.mvi.UserListIntent
import comtest.ct.cd.zulfikar.user.mvi.UserListViewEffect
import comtest.ct.cd.zulfikar.user.mvi.UserListViewState
import comtest.ct.cd.zulfikar.widget.EndlessRecyclerViewScrollListener
import comtest.ct.cd.zulfikar.widget.sortdialog.SortDialog
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
        private const val visibleThreshold = 3
        private const val RESET_PAGE = 1
        fun newInstance() = UserListFragment()
    }

    private val viewModel: UserListViewModel by viewModels()

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    private val intentSubject = PublishSubject.create<UserListIntent>()

    private lateinit var adapter: UserListAdapter

    private lateinit var sortDialog: SortDialog<UserListOrderBy>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processIntents(intents())
        setAdapter()
        setSwipeRefresh()
        setSortDialog()
        txtSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        intentSubject.onNext(
                            UserListIntent.LoadUserListByNameIntent(false, it)
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

    private fun setSortDialog() {
        sortDialog = SortDialog.createInstance(true, getString(R.string.title_sort))
        sortDialog.submitList(
            generateSortBy()
        )
        btnSort.setOnClickListener {
            sortDialog.show(
                requireActivity().supportFragmentManager,
                UserListFragment::class.java.simpleName
            )
        }
    }

    private fun generateSortBy(): List<UserListOrderBy> {
        val list = arrayListOf<UserListOrderBy>()
        list.addAll(UserListOrderBy.values())
        return list
    }

    private fun setSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            intentSubject.onNext(
                UserListIntent.LoadUserListByNameIntent(true, txtSearch.query.toString())
            )
        }
    }

    private fun setScrollListener(page: Int) {
        //add scroll listener
        userListRecyclerView.clearOnScrollListeners()
        val layoutManager = userListRecyclerView.layoutManager as LinearLayoutManager
        val scrollListener = object : EndlessRecyclerViewScrollListener(
            visibleThreshold,
            layoutManager
        ) {
            override fun onLoadMore() {
                intentSubject.onNext(
                    UserListIntent.LoadMoreUserListIntent(page = page, txtSearch.query.toString())
                )
                userListRecyclerView.clearOnScrollListeners()
            }
        }
        userListRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun setAdapter() {
        adapter = UserListAdapter()
        userListRecyclerView.layoutManager = LinearLayoutManager(
            requireContext()
        )
        val dividerItemDecoration = DividerItemDecoration(
            userListRecyclerView.context,
            (userListRecyclerView.layoutManager as LinearLayoutManager).orientation
        )
        userListRecyclerView.addItemDecoration(dividerItemDecoration)
        userListRecyclerView.adapter = adapter
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

        viewModel.getViewEffect()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe({
                handleViewEffect(it)
            }, {
                Timber.e(it)
            })

        sortDialog.getListener()
            .autoDisposable(scopeProvider)
            .subscribe({
                handleSort(it)
                sortDialog.dismiss()
            }, { exception ->
                Timber.e(exception)
            })
    }

    private fun handleSort(userListOrderBy: UserListOrderBy) {
        intentSubject.onNext(
            UserListIntent.SetSortSettingIntent(
                userListOrderBy,
                txtSearch.query.toString()
            )
        )
    }

    override fun intents(): Observable<UserListIntent> {
        return intentSubject.serialize()
    }

    override fun render(state: UserListViewState) {
        handleLoading(state)
        handleContent(state)
    }

    private fun handleViewEffect(userListViewEffect: UserListViewEffect) {
        when (userListViewEffect) {
            is UserListViewEffect.ShowResultErrorViewEffect -> {
                createSnackBar(
                    rootCoordinator,
                    R.string.error_something_wrong,
                    R.string.action_retry,
                    {
                        intentSubject.onNext(
                            UserListIntent.LoadUserListByNameIntent(
                                false,
                                txtSearch.query.toString()
                            )
                        )
                    },
                    R.id.snackbarHolder,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is UserListViewEffect.ShowResultEmptyViewEffect -> {
                createSnackBarNoAction(
                    rootCoordinator,
                    getString(R.string.result_not_found),
                    R.id.snackbarHolder
                ).show()
            }
        }
    }

    private fun handleLoading(state: UserListViewState) {
        if (state.isLoading) {
            if (state.isPullToRefresh) {
                swipeRefreshLayout.isRefreshing = true
            }
        } else {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun handleContent(state: UserListViewState) {
        adapter.submitList(state.userList)
        if (state.userList.isNotEmpty()) {
            layoutNotFound.visibility = View.GONE
            userListRecyclerView.visibility = View.VISIBLE
            setScrollListener(state.nextPage)
        } else {
            layoutNotFound.visibility = View.VISIBLE
            userListRecyclerView.visibility = View.GONE
            userListRecyclerView.clearOnScrollListeners()
        }
    }
}
