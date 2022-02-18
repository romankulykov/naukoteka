package uddug.com.domain.repositories.users_search.models

import java.util.*

data class UserStatus(val userId: String, val status: UserStatusDetail)

data class UserStatusDetail(val isOnline: Boolean, val lastSeen: Calendar?)
