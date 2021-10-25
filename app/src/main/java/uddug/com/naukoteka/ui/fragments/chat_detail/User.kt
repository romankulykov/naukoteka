package uddug.com.naukoteka.ui.fragments.chat_detail

import com.stfalcon.chatkit.commons.models.IUser

class User : IUser {

    private var id: String? = null
    private var name: String? = null
    private var avatar: String? = null
    private var online = false

    constructor(id: String?, name: String?, avatar: String?, online: Boolean) {
        this.id = id
        this.name = name
        this.avatar = avatar
        this.online = online
    }

    override fun getId(): String? {
        return id
    }

    override fun getName(): String? {
        return name
    }

    override fun getAvatar(): String? {
        return avatar
    }

    fun isOnline(): Boolean {
        return online
    }
}