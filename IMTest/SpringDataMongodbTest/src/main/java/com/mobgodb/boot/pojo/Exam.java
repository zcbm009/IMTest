package com.mobgodb.boot.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Builder
@Document("exam") // 相当于jpa中的@Table
public class Exam {
    @Indexed // 相当于jpa中的@Column
    private ObjectId id;
    @Indexed
    private String name;
    @Indexed
    private Integer age;
}
