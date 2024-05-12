package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFeed;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Qualifier("DaoFeedImpl")
public class DaoFeedImpl implements DaoFeed {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> getFeed(int userId) {
        String sql = "SELECT f.event_id, " +
                "f.user_id, " +
                "f.entity_id, " +
                "et.name as event_type, " +
                "o.name as operation, " +
                "f.timestamp " +
                "FROM feed as f " +
                "JOIN event_types AS et ON et.event_type_id = f.event_type_id " +
                "JOIN operations AS o ON o.operation_id = f.operation_id " +
                "WHERE f.user_id = ? " +
                "ORDER BY f.timestamp ASC";
        try {
            return jdbcTemplate.query(sql, eventRowMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Event addEvent(Event event) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("feed")
                .usingGeneratedKeyColumns("event_id");
        event.setEventId(simpleJdbcInsert.executeAndReturnKey(getMap(event)).intValue());
        return event;
    }

    private RowMapper<Event> eventRowMapper() {
        return (rs, rowNum) -> new Event(rs.getInt("event_id"),
                rs.getInt("user_id"),
                rs.getInt("entity_id"),
                rs.getString("event_type"),
                rs.getString("operation"),
                rs.getTimestamp("timestamp").toInstant().toEpochMilli());
    }

    private Map<String, Object> getMap(Event event) {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", event.getUserId());
        values.put("entity_id", event.getEntityId());
        values.put("event_type_id", EventType.valueOf(event.getEventType()).getId());
        values.put("operation_id", Operation.valueOf(event.getOperation()).getId());
        values.put("timestamp", Timestamp.from(Instant.ofEpochMilli(event.getTimestamp())));
        return values;
    }
}
