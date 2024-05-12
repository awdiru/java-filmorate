package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Event {
    Integer eventId;
    @NotNull
    Integer userId;
    @NotNull
    Integer entityId;
    @NotNull
    String eventType;
    @NotNull
    String operation;
    @NotNull
    Long timestamp;
}


