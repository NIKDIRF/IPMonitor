# IP Monitor Android

Приложение для отображения текущего IP-адреса с автоматическим обновлением

[![Download APK](https://img.shields.io/badge/Download-IPMonitor.apk-blue?style=for-the-badge&logo=android)](https://github.com/NIKDIRF/IPMonitor/raw/main/IPMonitor.apk)
## Основные функции
- Определение текущего публичного IP-адреса
- Автоматическое обновление каждые 5 секунд
- Три визуальных состояния:
  - 🔵 Синий - начальное состояние
  - 🟢 Зеленый - успешное получение IP
  - 🔴 Красный - ошибка соединения
- Копирование IP адреса одним нажатием

## Используемые технологии
- **Язык**: Kotlin 100%
- **Архитектура**: MVVM
- **DI**: Dagger Hilt
- **Сетевые запросы**: Retrofit + Gson
- **Асинхронность**: Kotlin Coroutines + Flow
- **UI**: ViewBinding + Material Components

## Скриншоты 

| Состояние | **Стандартное** | **Успешное получение IP** | **Ошибка соединения** |
|-----------|----------|-----------|----------|
| Скриншот | <img src="https://github.com/NIKDIRF/IPMonitor/blob/main/IPMonitor%20default.png" width="250"> | <img src="https://github.com/NIKDIRF/IPMonitor/blob/main/IPMonitor%20ok.png" width="250"> | <img src="https://github.com/NIKDIRF/IPMonitor/blob/main/IPMonitor%20error.png" width="250"> |

## Системные требования
- Android 10 (API 29) и выше
- Интернет-соединение
