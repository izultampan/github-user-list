package comtest.ct.cd.zulfikar.user.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quipper.common.mvi.MviViewModel
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.GetUserList
import comtest.ct.cd.zulfikar.user.mvi.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineDispatcher

class UserListViewModel @ViewModelInject constructor(
    fetchUserList: FetchUserList,
    getUserList: GetUserList,
    private val userListActionFilter: UserListActionFilter,
    private val userListReducer: UserListReducer
) : ViewModel(), MviViewModel<UserListIntent, UserListViewState> {

    private val intentSubject = PublishSubject.create<UserListIntent>()

    private val actionProcessor = UserListActionProcessor(
        viewModelScope = viewModelScope,
        fetchUserList = fetchUserList,
        getUserList = getUserList
    )
    private val stateObservable: Observable<UserListViewState> = compose()

    private fun compose(): Observable<UserListViewState> {
        return intentSubject
            .compose(userListActionFilter.filter())
            .map(userListActionFilter::actionFromIntent)
            .flatMap { action ->
                actionProcessor.processAction(action)
            }
            .scan(UserListViewState.InitialState, userListReducer::reduce)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    override fun processIntents(intents: Observable<UserListIntent>) {
        intents.subscribe(intentSubject)
    }

    override fun getStates(): Observable<UserListViewState> {
        return stateObservable.serialize()
    }
}
