package com.imooc.demo.common;


import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

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

    //排序
    public interface ProductListOrderBy{
        //价格排序
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
        //修改时间排序
        Set<String> UPDATETIME_ASC_DESC = Sets.newHashSet("update_time_desc","update_time_asc");
    }

    public interface Cart{
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";//库存不够
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";//库存充足
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
