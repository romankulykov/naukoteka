package medved.studio.data.services.auth

import medved.studio.data.services.AuthApiService
import toothpick.InjectConstructor

@InjectConstructor
class AuthApiHolder {
    var authApi: AuthApiService? = null
}
