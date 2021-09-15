package medved.studio.data.services.auth

import toothpick.InjectConstructor

@InjectConstructor
class AuthApiHolder {
    var authApi: AuthApiService? = null
}
