Факультативные задания к мобильной разработке

Требования к приложению
=======================

1.  SplashScreen

Для входа в приложение требуется пройти аутентификацию на сервере github (свой
аккаунт).

2) Меню:

А) Окно списка репозиториев пользователя. Отображение данных возможно произвести
либо с помощью WebView, либо используя RecycleView с запросами API (везде
требуется отправка полученного token);

Б) Карта (google_maps&yandex_maps). С отображением маршрута из адреса
университета до дома, используя какой-либо вид транспорта (условная точка).

В) Отображение списка контактов из телефонной книги\*;

Г) Отображение основной информации об устройстве (ip, android_version и т.д.);\*

Д) Отображение изменяемых значений работы одного из датчиков и получения
фотографии с последующим сохранением в память; \*

Е) Выход (Logout).

Задачи приложения и их описание 
================================

SplashScreen

На окне аутентификации присутствует поле login и password, после верного
введения своих учетных данных на GitHub и нажатии кнопки Sign In происходит
проверка наличия у пользователя второй аутентификации и при наличии таковой
предлагается ввести код, после чего происходит вход, если же её нет то вход
происходит без запроса кода.

![img1](https://i.imgur.com/RBtZneD.png)

Просьба ввода второго фактора:

![img2](https://i.imgur.com/Ng0ZWPF.png)

После входа открывается layout с названием проекта и значком меню слева сверху

![img3](https://i.imgur.com/Q7YMb9q.png)

В меню реализованы пункты из задания : Карта, калькулятор(собственное добавление
к проекту) , контракты, датчики и камера, список репозиториев, контакты и выход
из приложения

![img4](https://i.imgur.com/YLu5Ii9.png)

**Карта**:

При выборе этого пункта откроется Google карта с путем до кампуса вуза на
Стромынке, режим передвижения – на машине, компас, кнопки приближения и
отдаления присутствуют.

![img5](https://i.imgur.com/733Ec5H.png)

Камера:
-------

Во фрагменте с камерой так же реализована демонстрация показаний
гироскопа(одного из датчиков устройства). Ниже показаний датчиков
демонстрируется последняя сделанная фотография, пока она не сделана там
находится базовая картинка, кнопки под фото выполняют соответственно:
переключение на камеру для совершения фотографирования, сохранение фотографии в
альбом.

![img6](https://i.imgur.com/P9ggIg6.png)

Репозитории:
------------

При выборе этого пункта меню на экран будет выведен фрагмент со списком всех
репозиториев, имеющихся у залогинившегося пользователя.

![img7](https://i.imgur.com/Q6TxrkI.png)

Контакты:
---------

При выборе этого пункта, на экран будет выведен фрагмент со списком всех
контактов имеющихся на устройстве.

**Графический макет программы**

![img8](https://i.imgur.com/lxKyKal.png)

**Краткое описание используемых шаблонов проектирования**

В данном проекте применяется шаблон проектирования Model-View-Presenter, с общим
принципом реализации, показанным на блок-схеме ниже.

![img9](https://i.imgur.com/RuxzBfA.png)

В роли view, в общем виде, является фрагмент программы - SensorFragment, который
предоставляет все необходимые методы через интерфейс ISensorFragmentActivity
презентеру. Именно в нем содержится основная логика управления этим модулем. В
случае, если фрагменту потребуется получить какие-то данные от презентера, то
модуль — будет это делать через интерфейс ISensorFragmentPresenter.

В классе фрагмента существует экземпляр интерфейса для взаимодействия двух
модулей: **private** ISensorFragmentPresenter **iSensorFragmentPresenter**;

При создании фрагмента происходит инициализация презентера:

**if** (**iSensorFragmentPresenter** == **null**) {  
**iSensorFragmentPresenter** = **new** SensorFragmentPresenter(**this**);  
}

таким образом модули завязываются друг на друга.

Соединение с моделью также происходит через интерфейс. Подобным образом создан
класс GitHubConnection. Модуль отвечает за отправку через AsyncTask данных на
сервера GitHub для авторизации пользователя.

**Описание API приложения**

Для использования модульного API требуется авторизация через сервис GitHub.

Аутенфикация требует от конечного пользователя ввода логина и пароля для входа в
систему. В случае подключенного второго фактора, API также запросит его на ввод.

После успешной авторизации будет доступен основной функционал приложения.

API обладает следующими функциями:

-   Вывод списка контактов, находящихся на устройстве

-   Вывод репозиториев авторизовавшегося пользователя

-   Вывод данных с гироскопа

-   Создание и сохранение фотографий

-   Калькулятор

-   Карта с построением маршрута от дома (задано внутри программы) до филиала
    университета Стромынка 20.
