package medved.studio.pharmix.di.utils.http


import medved.studio.data.repositories.auth.AuthRepositoryMapper
import medved.studio.data.services.auth.AuthApiHolder
import medved.studio.domain.utils.logging.ILogger
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import toothpick.InjectConstructor

@InjectConstructor
class RefreshTokenAuthenticator(
    private val authApiHolder: AuthApiHolder,
    private val mapper: AuthRepositoryMapper,
    private val logger: ILogger
) : Authenticator {

    private val accessToken get() =  ""
    private val refreshToken get() = ""

    private val authApi get() = authApiHolder.authApi!!

    override fun authenticate(route: Route?, response: Response): Request? {
        logger.info("Refreshing tokens..")

        if (refreshToken.isNullOrEmpty()) {
            logger.warn("Cannot refresh tokens because refresh token doesn't exist")
            return null
        }

        if (response.request.header("Authorization") == "Bearer $refreshToken") {
            logger.info("Refresh token request has failed. Giving up")
            //sessionInteractor.onSessionExpired()
            return null
        }

        /*authApi.refreshToken("Bearer $refreshToken")
            .doOnSuccess {
                logger.info("Tokens were successfully refreshed")
                serverTokenCache.entity = mapper.mapAuthTokenToDomain(it)
            }
            .ignoreElement()
            .onErrorComplete()
            .blockingAwait()*/

        return response.request.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }
}