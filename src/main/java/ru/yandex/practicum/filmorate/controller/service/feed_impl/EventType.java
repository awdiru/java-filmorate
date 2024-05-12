package ru.yandex.practicum.filmorate.controller.service.feed_impl;

public enum EventType {
    LIKE(1),
    REVIEW(2),
    FRIEND(3);

    private final int id;

    EventType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
