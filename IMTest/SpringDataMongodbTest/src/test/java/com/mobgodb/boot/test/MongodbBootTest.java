package com.mobgodb.boot.test;

import com.mobgodb.boot.dao.ExamDao;
import com.mobgodb.boot.pojo.Exam;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbBootTest {

    @Autowired
    ExamDao examDao;

    @Test
    public void testFindList() {
        examDao.findList(1, 5).forEach(System.out::println);
    }

    @Test
    public void testFindListByName() {
        System.out.println(examDao.findListByName("ccs"));
    }

    @Test
    public void testSave() {
        Exam hyk = examDao.save(Exam.builder().name("hyk").age(12).build());
        System.out.println(hyk);
    }

    @Test
    public void testUpdate() {
        UpdateResult update = examDao.update(
                Exam.builder().id(new ObjectId("625f062e78f3c82212637dff"))
                        .name("hct").age(18)
                        .build());
        System.out.println(update);
    }

    @Test
    public void testRemove(){
        Exam delete = examDao.delete(new ObjectId("625f062e78f3c82212637dff"));
        System.out.println(delete);
    }

}
