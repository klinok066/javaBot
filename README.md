# Бот для изучения английских слов (флеш-карточки с английскими словами)

**Задача:** создать Restful API  для бота и навыка Алисы по изучению английского

**Функционал API:**

1. Добавлять новые слова в базу данных (только английские)
2. Возможность тренировки выученных слов (как по всем, так и по группам)
3. Напоминание про повторение слов

## Добавление новых слов

При добавлении слова у нас должна быть возможность:

1. Добавить его в определенную группу (обязательный параметр)
2. Задать перевод слова (обязательный параметр): перевод слова можно указать самостоятельно или воспользоваться автоматическим переводом
3. Само слово, которое хотим добавить

## Тренировка выученных слов

Тренировка должна быть: по всем выученным, по некоторой части, по всей группе слов или по части выученных слов

### По всем выученным словам

`/test_all <режим>` - начать тестирование по всем словам из базы данных

Тут есть **сложный режим** и **легкий**

**Сложный режим** - это надо написать боту самому перевод слова

**Легкий режим** - это надо выбрать 1 из 4 переводов

**По умолчанию стоит легкий режим**, т. е. если не указать какой режим, то автоматически сервер выберет легкий

Идет проверка выученных слов до тех пор, пока пользователь не напишет `/stop_test`. 

### По некоторой части слов

`/test <количество слов> <режим>` - начать тестирование по некоторой части слов из всей базы данных

Пользователь в этом режиме пишет количество слов для проверки, стандартно установленно значение 10

Выбор режима также, как и в прошлом разделе

### По всем словам из группы

`/test_in_group_all <группа> <режим>` - начать тестирование по всем словам из группы

Тут такой же выбор режима, как и в первом разделе

Чтобы остановить тестирование, надо написать команду `/stop_test`

### По некоторым словам из группы

`/test_in_group <группа> <количество> <режим>` - начать тестирование по некоторому количеству слов из группы

Выбор режима такой же, как и первом разделе

## Напоминание о повторении слов

Раз в день должно было сделанно уведомление в боте, которое звучит так: *О, а какое щас время? Правильно, время учить английский!*

## Список команд:

`/start` - запустить бота

`/group_list` - список всех групп

`/group_create <название>` - создать группу

`/word_list` - список всех слов с паггинацией

`/word_add <слово> <перевод> <группа>` - добавление слова. Перевод можно самим указать, но если написать `_translate`, то перевод будет автоматический через Yandex API, параметр группа не обязателен, все остальные обязательны

`/word_to <слово> <группа>` - переопределение группы у существующего слова

## Архитектура:

    -----------------------  --------------------------
    | Алиса command block |  | Telegram command block |
    -----------------------  --------------------------
                     ↑↓          ↑↓
        -------------------------------------------
        |          Обработчик запросов            |
        -------------------------------------------
                            ↑↓
                    -------------------
                    |       API       |
                    -------------------
                            ↑↓   
                    -------------------
                    |   База данных   |
                    -------------------
                    
## Этап 1


### Задачи:
1. Написать сервер, подключиться к API телеграмма
2. На сообщение: *Привет, бот!* Ответить: *Привет, <имя в телеграме>!*
3. Поставить бота на heroku

Обязанности Ильи: Написать сервер и интерфейс, который подключается к API телеграмма и предоставляет соответствующие методы, покрыть тестами
Обязанности Артема: Релизовать ответы на сообщения и задеплоить его на heroku, покрыть тестами
