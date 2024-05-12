package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface FeedStorage {

    List<Event> getFeed(int userId);

    Event addEvent(Event event);
}
