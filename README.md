# SpaceXTest
 SpaceX TestApp
 
 Api usage [SpaceXApi](https://github.com/r-spacex/SpaceX-API/tree/master/docs/)
 
# Task
```
Разработать приложение справочник по компании SpaceX на основе открытого API https://github.com/r-spacex/SpaceX-API/tree/master/docs/

В приложении должно быть два экрана.

На первом экране необходимо отобразить список миссий (https://api.spacexdata.com/v4/launches).
Условия:
- список должен работать с пагинацией (размер страницы 10 элементов)
- загружать запуски с начала 2021 года (date_utc)
- сортировка в порядке убывания по дате запуска (date_utc)
- в ячейке необходимо показать:
  - иконку миссии (links.patch.small)
  - наименование (name)
  - количество повторных использований первой ступени (cores.flight)
  - статус миссии (success)
  - дата запуска в формате ДД-ММ-ГГГГ

По тапу на ячейку должен открывать второй экран.

На втором экране необходимо показать детали миссии.
Условия:
- на странице показать:
  - наименование миссии
  - логотип (links.patch.large)
  - количество повторных использований первой ступени (cores.flight)
  - статус миссии (success)
  - детали (details)
  - дата и время миссии в формате ЧЧ-ММ ДД-ММ-ГГГГ
  - список экипажа: ФИО, агенство, статус (https://api.spacexdata.com/v4/crew)


Требования для Android:
- использовать Kotlin
- минимальная поддерживаемая версия API 22
- поддержка смены ориентации экрана
- разработка UI в соответствии с Material Guidlines https://material.io/design/guidelines-overview
- использовать паттерн проектирования MVVM
- Single Activity с Navigation Component
- сетевой стек построить на Retrofit+Moshi/Gson
- плюсом будет использование Kotlin Coroutines + Flow, а также DI Dagger 2
- для загрузки картинок использовать библиотеку Coil

Критерии оценки:
- оформление и чистота кода
- работа с жизненным циклом контроллера (ViewController/Activity/Fragment)
- правильность организации работы со списком
- использование паттерна MVVM на практике
- умение разрабатывать производительный UI
- работа с памятью
- работа с многопоточностью
- соответствие требованиям
- корректность применения библиотек и технологий
```

# Screens
## Home Screen
![image](https://user-images.githubusercontent.com/28680051/194705218-ee18c81f-02de-4e2a-aab2-21c478cb2a0d.png)


## Page Screen
![image](https://user-images.githubusercontent.com/28680051/194705289-d99ef20e-93fe-4499-9d35-28ff239bda9a.png)
