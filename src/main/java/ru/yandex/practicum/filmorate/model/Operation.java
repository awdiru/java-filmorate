package ru.yandex.practicum.filmorate.model;

public enum Operation {
    REMOVE(1),
    ADD(2),
    UPDATE(3);

    private final int id;

    Operation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
