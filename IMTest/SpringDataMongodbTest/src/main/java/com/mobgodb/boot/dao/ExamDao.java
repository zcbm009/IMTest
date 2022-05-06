package com.mobgodb.boot.dao;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.mobgodb.boot.pojo.Exam;

import java.util.List;

/**
 * 关于增删改查的测试
 */
@Component
public class ExamDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ExamDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 查询列表
     *
     * @return
     */
    public List<? extends Exam> findList(int page, int size) {

        return mongoTemplate.find(
                new Query().skip(page - 1).limit(size)
                        .with(Sort.by(Sort.Direction.ASC, "_id")),
                Exam.class);
    }

    /**
     * 条件查询
     *
     * @param name
     * @return
     */
    public Exam findListByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Exam.class);
    }

    /**
     * 增加
     *
     * @param exam
     * @return
     */
    public Exam save(Exam exam) {
        return mongoTemplate.save(exam);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public Exam delete(ObjectId id) {
        return mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(id)), Exam.class);
    }


    /**
     * 更新
     *
     * @param exam
     * @return
     */
    public UpdateResult update(Exam exam) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(exam.getId())),
                Update.update("name", exam.getName()).set("age", exam.getAge()), Exam.class);
    }

}
