package com.test.im.dao;

import com.test.im.pojo.Message;
import com.test.im.pojo.UserMap;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDaoTest {

    @Autowired
    private MessageDao messageDao;

    @Test
    public void testUpdateMessage() {

    }

    @Test
    public void testRemoveMessage() {

    }

    @Test
    public void testFindMessageByFromAndTo() {
//        System.out::println
        List<Message> listByFromAndTo = messageDao.findListByFromAndTo(1111L, 1112L, 1, 3);
        listByFromAndTo.forEach(System.out::println);
    }

    @Test
    public void testSaveMessage() {
        messageDao.saveMessage(Message.builder()
                .id(ObjectId.get())
                .msg("hello")
                .from(UserMap.USER_MAP.get(1111L))
                .to(UserMap.USER_MAP.get(1112L))
                .build());
        messageDao.saveMessage(Message.builder().id(ObjectId.get()).msg("hi")
                .from(UserMap.USER_MAP.get(1112L))
                .to(UserMap.USER_MAP.get(1111L))
                .build());
        messageDao.saveMessage(Message.builder().id(ObjectId.get()).msg("how are you")
                .from(UserMap.USER_MAP.get(1111L))
                .to(UserMap.USER_MAP.get(1112L))
                .build());
        messageDao.saveMessage(Message.builder().id(ObjectId.get()).msg("I'm fine thank you, and you ")
                .from(UserMap.USER_MAP.get(1112L))
                .to(UserMap.USER_MAP.get(1111L))
                .build());
        messageDao.saveMessage(Message.builder().id(ObjectId.get()).msg("I'm fine too")
                .from(UserMap.USER_MAP.get(1111L))
                .to(UserMap.USER_MAP.get(1112L))
                .build());
        messageDao.saveMessage(Message.builder().id(ObjectId.get()).msg("bye")
                .from(UserMap.USER_MAP.get(1112L))
                .to(UserMap.USER_MAP.get(1111L))
                .build());
    }

}
