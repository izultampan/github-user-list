package comtest.ct.cd.zulfikar.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import comtest.ct.cd.zulfikar.R
import comtest.ct.cd.zulfikar.user.ui.main.UserListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserListFragment.newInstance())
                .commitNow()
        }
    }
}
