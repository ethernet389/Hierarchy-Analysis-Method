# Solve-on-Java
  Реализация метода анализа иерархий


## Формат входного файла
    1. Количество критериев оценивания
    2. Матрица "важности" каждого критерия относительно других критериев
    3. Количество альтернатив
    4. Матрицы альтернатив по каждому критерию ("качество" альтернативы А к В по критерию M)
**Примечание 1 (опц.):** В конце указывается порядок вывода рейтинга альтернатив<br>
**Примечание 2:** Разделение на строки необязательно (данные можно записывать в одну строку)<br>

## Структура

  - [*src/Main.java*](https://github.com/ethernet389/Solve-on-Java/blob/main/src/Main.java) - исполняемый код ([completeTask](https://github.com/ethernet389/Solve-on-Java/blob/main/src/Main.java#L32) - функция для решения задачи)<br/>
  - [*src/MatrixFunctions.java*](https://github.com/ethernet389/Solve-on-Java/blob/main/src/MatrixFunctions.java) - вспомогательные функции для реализации метода анализа иерархий<br>
  - [*Jama-1.0.3.jar*](https://github.com/ethernet389/Solve-on-Java/blob/main/Jama-1.0.3.jar) - библиотека, реализующая матрицы<br/>
  - [*data_sets*](https://github.com/ethernet389/Solve-on-Java/blob/main/data_sets) - наборы данных из упражнений (Таха)<br/>
##
###### Сделано в intelliJ IDEA
