package com.cocotalk.chat.repository;

import com.cocotalk.chat.domain.entity.room.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, ObjectId>, QuerydslPredicateExecutor<Room> {
    @Query("{ members: { $elemMatch: { userId: ?0, joining: true } } }")
    List<Room> findByUserIdAndJoiningIsTrue(Long userId);
}
