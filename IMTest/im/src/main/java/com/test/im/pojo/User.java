package com.test.im.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class User {
    Long id;
    String name;
}
