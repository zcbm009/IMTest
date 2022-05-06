package com.test.im.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.test.im.pojo.Message;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageDaoImpl implements MessageDao {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Message> findListByFromAndTo(Long from, Long to, Integer page, int rows) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                // 获取from发送给to的消息
                Criteria.where("from._id").is(from).and("to._id").is(to),
                // 获取to发送给from的消息
                Criteria.where("to._id").is(from).and("from._id").is(to)
        ));
        return mongoTemplate.find(
                query.with(PageRequest.of(page - 1, rows, Sort.by(Sort.Direction.ASC, "sendDate"))),
                Message.class);
    }

    @Override
    public Message findMessageById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Message.class);
    }

    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {
        Update update = Update.update("status", status);
        if(status == 1){
            update.set("sendDate", new Date());
        }else if(status == 2){
            update.set("readDate", new Date());
        }
        return mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                update,
                Message.class);
    }

    @Override
    public Message saveMessage(Message message) {
        message.setSendDate(new Date());
        message.setStatus(1);
        return mongoTemplate.save(message);
    }

    @Override
    public DeleteResult deleteMessage(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Message.class);
    }
}
