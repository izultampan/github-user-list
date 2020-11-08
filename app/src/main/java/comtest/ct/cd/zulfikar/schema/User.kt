package comtest.ct.cd.zulfikar.schema

data class User(

    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: List<Items> = emptyList()
)

