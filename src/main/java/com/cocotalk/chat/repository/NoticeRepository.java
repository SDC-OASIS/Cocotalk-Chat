package com.cocotalk.chat.repository;

import com.cocotalk.chat.domain.entity.room.Notice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<Notice, ObjectId>, QuerydslPredicateExecutor<Notice> {
}
