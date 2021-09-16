package medved.studio.pharmix.presentation.tutorial

import medved.studio.data.cache.first_launch.FirstLaunchCache
import medved.studio.domain.entities.TutorialEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.navigation.Screens.Tutorial
import toothpick.InjectConstructor

@InjectConstructor
class TutorialPresenter(
    private val router: AppRouter,
    private val isFirstLaunched : FirstLaunchCache,
) : BasePresenterImpl<TutorialView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTutorialInformation(authorizationResources)
    }

    fun finishTutorial() {
        isFirstLaunched.entity = false
        router.newRootScreen(Screens.Login())
    }

    fun exit() {
        router.exit()
    }

    private val authorizationResources
        get() = listOf(
            TutorialEntity(
                R.drawable.ic_tutorial_1,
                R.string.the_best_scientific_works
            ),
            TutorialEntity(
                R.drawable.ic_tutorial_2,
                R.string.smart_community
            ),
            TutorialEntity(
                R.drawable.ic_tutorial_3,
                R.string.new_ideas
            )
        )
}