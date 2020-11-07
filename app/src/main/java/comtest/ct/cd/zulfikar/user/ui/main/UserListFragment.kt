package comtest.ct.cd.zulfikar.user.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import comtest.ct.cd.zulfikar.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            viewModel.fetchUserList().forEach {
                Log.v("UserListFragment", "${it.id}")
            }
        }
    }
}
