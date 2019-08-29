package cn.wasu.community.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long createTime;
    private Long modifyTime;
    private String avatarUrl;
}
