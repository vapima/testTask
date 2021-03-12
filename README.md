# Test Task
A small test task with multithreading and reading files.

###  Привет!
Небольшая задачка с потоками.
Сделано два варианта, первый не особо подходит по условиям задачи, поэтому сделан второй.

***
### Задача:
Имеется находится csv-файл exchange_rates_2001-2021.csv (разделители – табуляция)
с данными о курсе доллара США с 1 января 2000 года по текущий день
(с сайта ЦБ http://cbr.ru/currency_base/dynamics/).
Надо написать консольное приложение, которое в начале своей работы должно запустить три потока,
каждый из которых будет читать строки из исходного файла и записывать прочитанные строки в отдельный файл
(для каждого потока свой).
При чтении потоки должны соблюдать следующе правило: строка,
прочитанная одним потоком и записанная им в свой файл, не должна попасть в файлы, наполняемые другими потоками.

Приложение должно дождаться окончания работы всех потоков и после этого запустить процедуру валидации,
которая должна подтвердить, что в трех получившихся файлах содержатся все строки из исходного файла и
при этом каждая строка из исходного файла содержится только в одном из итоговых файлов.
(Т.е. 3 запущенных потока распределили содержимое исходного файла в три других файла,
ничего не потеряв и не записав дважды). Результат валидации вывести на экран в любом удобном виде.
***
