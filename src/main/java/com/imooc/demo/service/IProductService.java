package com.imooc.demo.service;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.vo.ProductDetailVo;

/**
 * ClassName: IProductService
 * Description: TODO商品接口层
 * Author: Leo
 * Date: 2020/3/11-7:07
 * email 1437665365@qq.com
 */
public interface IProductService {
    //增加或更新商品
    ServerResponse saveOrUpdateProduct(Product product);
    //修改商品的销售状态
    ServerResponse setSaleStatus(Integer productId,Integer status);
    //获取商品详情
    ServerResponse<ProductDetailVo>  manageProductDetail(Integer productId);
    //分页查询所有记录
    ServerResponse getProductListPageHelper(int pageNum,int pageSize);
    //根据姓名或productId进行搜素,分页
    ServerResponse searshProductListVoPage(Integer productId, String productName, int pageNum, int pageSize);
    //根据姓名或productId进行搜素,不分页
    ServerResponse searshProductListVo(String productName,Integer productId);
}