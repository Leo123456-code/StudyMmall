package com.imooc.demo.dao;

import com.imooc.demo.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    //查询所有的商品
    List<Product> findAllProductList();
    //根据姓名或productId进行搜素
    List<Product> selcetByNameAndProductId(@Param("name") String productName,@Param("id")Integer productId);

}