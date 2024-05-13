package ru.yandex.practicum.filmorate.controller.storage.feed_impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFeed;
import ru.yandex.practicum.filmorate.controller.storage.FeedStorage;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

@Component
@AllArgsConstructor
@Qualifier("FeedStorageDao")
public class FeedStorageDao implements FeedStorage {

    @Qualifier("DaoFeedImpl")
    private final DaoFeed daoFeed;

    @Override
    public List<Event> getFeed(int userId) {
       return daoFeed.getFeed(userId);
    }

    @Override
    public Event addEvent(Event event) {
        return daoFeed.addEvent(event);
    }
}
