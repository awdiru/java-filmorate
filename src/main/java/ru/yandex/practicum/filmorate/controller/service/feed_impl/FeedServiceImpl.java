package ru.yandex.practicum.filmorate.controller.service.feed_impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.FeedService;
import ru.yandex.practicum.filmorate.controller.storage.FeedStorage;
import ru.yandex.practicum.filmorate.model.Event;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Qualifier("FeedServiceImpl")
public class FeedServiceImpl implements FeedService {

    @Qualifier("FeedStorageDao")
    private final FeedStorage feedStorage;

    @Override
    public List<Event> getFeed(int userId) {
        return feedStorage.getFeed(userId);
    }

    @Override
    public void addAddFriendEvent(int userId, int friendId) {
        Event event = new Event(null,
                userId,
                friendId,
                EventType.FRIEND.name(),
                Operation.ADD.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addRemoveFriendEvent(int userId, int friendId) {
        Event event = new Event(null,
                userId,
                friendId,
                EventType.FRIEND.name(),
                Operation.REMOVE.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addAddLikeEvent(int userId, int entityId) {
        Event event = new Event(null,
                userId,
                entityId,
                EventType.LIKE.name(),
                Operation.ADD.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addRemoveLikeEvent(int userId, int entityId) {
        Event event = new Event(null,
                userId,
                entityId,
                EventType.LIKE.name(),
                Operation.REMOVE.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addAddReviewEvent(int userId, int reviewId) {
        Event event = new Event(null,
                userId,
                reviewId,
                EventType.REVIEW.name(),
                Operation.ADD.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addRemoveReviewEvent(int userId, int reviewId) {
        Event event = new Event(null,
                userId,
                reviewId,
                EventType.REVIEW.name(),
                Operation.REMOVE.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

    @Override
    public void addUpdateReviewEvent(int userId, int reviewId) {
        Event event = new Event(null,
                userId,
                reviewId,
                EventType.REVIEW.name(),
                Operation.UPDATE.name(),
                Instant.now().toEpochMilli());
        feedStorage.addEvent(event);
    }

}
