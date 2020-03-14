package com.imooc.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * ClassName: Const
 * Description: TODO常量
 * Author: Leo
 * Date: 2020/3/9-22:41
 * email 1437665365@qq.com
 */
@Data
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final  String USERNAME = "username";

    public static final  String EMAIL = "email";

    public interface Role{
        int ROLE_CUSTOMER = 0 ;//普通用户
        int ROLE_ADMIN = 1 ;//管理员
    }

    @Getter
    @AllArgsConstructor
    public enum ProductStatusEnum{
        ON_SALE(1,"在售"),
        ON_PULL(2,"下架"),
        ;

        private Integer code;

        private String message;

    }
}
