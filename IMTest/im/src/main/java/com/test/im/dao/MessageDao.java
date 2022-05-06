package com.test.im.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.test.im.pojo.Message;
import org.bson.types.ObjectId;

import java.util.List;

public interface MessageDao {

    /**
     * 查看点对点的聊天记录
     * @param from
     * @param to
     * @param page
     * @param rows
     * @return
     */
    List<Message> findListByFromAndTo(Long from, Long to, Integer page, int rows);

    Message findMessageById(String id);

    /**
     * 更新消息状态
     * @param id
     * @param status
     * @return
     */
    UpdateResult updateMessageState(ObjectId id, Integer status);

    Message saveMessage(Message message);

    DeleteResult deleteMessage(String id);
}
