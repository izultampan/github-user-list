package comtest.ct.cd.zulfikar.user.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import comtest.ct.cd.zulfikar.repository.UserRepository
import comtest.ct.cd.zulfikar.schema.User

class UserListViewModel @ViewModelInject  constructor(
    private val userRepository: UserRepository,

): ViewModel() {

    suspend fun fetchUserList(): List<User> {
        userRepository.fetchUserList()
        return userRepository.getUserList()
    }
}
