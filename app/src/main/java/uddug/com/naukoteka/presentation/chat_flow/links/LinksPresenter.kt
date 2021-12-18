package uddug.com.naukoteka.presentation.chat_flow.links

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.LinksEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
class LinksPresenter(val router: AppRouter) : BasePresenterImpl<LinksView>() {

    private val listOfLinks = listOf(
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        ),
        LinksEntity(
            "Домашняя работа", "Наличие компьютера или ноутбука " +
                    "(телефон или планшет не подойдут), стабильного интернета, Веб-камеры;",
            "https://forms.gle/FCj6YqGBbnn43ugBH forms.gle/FCj6YqGBbnnugBHz9z9"
        )
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLinks(listOfLinks)
    }

    fun exit() {
        router.exit()
    }
}