package comtest.ct.cd.zulfikar.user.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quipper.common.mvi.MviViewModel
import comtest.ct.cd.zulfikar.usecase.FetchUserList
import comtest.ct.cd.zulfikar.usecase.SetQuery
import comtest.ct.cd.zulfikar.usecase.SetSortSetting
import comtest.ct.cd.zulfikar.user.mvi.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class UserListViewModel @ViewModelInject constructor(
    fetchUserList: FetchUserList,
    setSortSetting: SetSortSetting,
    setQuery: SetQuery,
    private val userListActionFilter: UserListActionFilter,
    private val userListReducer: UserListReducer,
    private val userListViewEffectSender: UserListViewEffectSender
) : ViewModel(), MviViewModel<UserListIntent, UserListViewState> {

    private val intentSubject = PublishSubject.create<UserListIntent>()

    private val actionProcessor = UserListActionProcessor(
        viewModelScope = viewModelScope,
        fetchUserList = fetchUserList,
        setSortSetting = setSortSetting,
        setQuery = setQuery
    )
    private val stateObservable: Observable<UserListViewState> = compose()

    private fun compose(): Observable<UserListViewState> {
        return intentSubject
            .compose(userListActionFilter.filter())
            .map(userListActionFilter::actionFromIntent)
            .flatMap { action ->
                actionProcessor.processAction(action)
            }
            .doOnNext(userListViewEffectSender::handleViewEffect)
            .scan(UserListViewState.initialState(), userListReducer::reduce)
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

    fun getViewEffect(): Observable<UserListViewEffect> {
        return userListViewEffectSender.getViewEffect()
    }
}
