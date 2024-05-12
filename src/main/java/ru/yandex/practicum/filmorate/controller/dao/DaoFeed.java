package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface DaoFeed {

    /**
     * Вернуть ленту событий пользователя
     *
     * @param userId идентификатор пользователя
     * @return лента событий
     */
    List<Event> getFeed(int userId);

    /**
     * Добавить событие
     *
     * @param event событие
     * @return добавленое событие
     */
    Event addEvent(Event event);
}
