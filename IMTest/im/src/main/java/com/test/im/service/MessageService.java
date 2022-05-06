package com.test.im.service;

import com.mongodb.client.result.UpdateResult;
import com.test.im.dao.MessageDao;
import com.test.im.pojo.Message;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao messageDao;

    public List<Message> findMessageByPage(Long from, Long to, Integer page, int rows){
        return messageDao.findListByFromAndTo(from, to, page, rows);
    }

    public UpdateResult updateMessageStatus(ObjectId id, Integer status){
        return messageDao.updateMessageState(id, status);
    }

}
