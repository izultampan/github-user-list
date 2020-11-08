package comtest.ct.cd.zulfikar.user

import androidx.annotation.StringRes
import comtest.ct.cd.zulfikar.R
import comtest.ct.cd.zulfikar.repository.impl.UserRepositoryImpl
import comtest.ct.cd.zulfikar.widget.sortdialog.SortLabel

enum class UserListOrderBy(val key: String, @StringRes val labelId: Int) :
    SortLabel {
    NAME_ASC(
        key = UserRepositoryImpl.NAME_ASC,
        labelId = R.string.sort_by_name_asc
    ),
    NAME_DESC(
        key = UserRepositoryImpl.NAME_DESC,
        labelId = R.string.sort_by_name_desc
    );

    override fun getStringId(): Int {
        return labelId
    }

    override fun getKeyId(): String {
        return key
    }
}
