package com.imooc.demo.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.dao.CategoryMapper;
import com.imooc.demo.dao.ProductMapper;
import com.imooc.demo.pojo.Category;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.service.IProductService;
import com.imooc.demo.util.PropertiesUtil;
import com.imooc.demo.vo.ProductDetailVo;
import com.imooc.demo.vo.ProductListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ProductServiceImpl
 * Description: TODO商品接口实现类
 * Author: Leo
 * Date: 2020/3/11-7:09
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        /**
         * @Description //TODO 增加或修改商品
           @Author Leo
         * @Date 7:27 2020/3/11
         * @Param [product]
         * @return com.imooc.demo.common.ServerResponse
        */
        if(product != null){
            //判断子图是否为空
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImagesArray = product.getSubImages().split(",");
                //选取图片的第一张图作为商品主图
                if(subImagesArray.length > 0){
                    product.setMainImage(subImagesArray[0]);
                }
            }
            //如果没有商品id,表示没有这个商品就是新增,否则就是修改
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if(rowCount == 0){
                    return ServerResponse.createByErrorMessage("更新商品失败");
                }
                return ServerResponse.createBySuccess("更新商品成功");
            }else {
                int rowCount = productMapper.insert(product);
                if(rowCount == 0){
                    return ServerResponse.createByErrorMessage("新增商品失败");
                }
                return ServerResponse.createBySuccess("新增商品成功");
            }
        }else {
            return ServerResponse.createByErrorMessage("新增或更新商品参数不正确");
        }
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        /**
         * @Description //TODO 修改商品的销售状态
           @Author Leo
         * @Date 20:37 2020/3/11
         * @Param [productId, status]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount == 0){
            return ServerResponse.createByErrorMessage("修改商品销售状态失败");
        }
        return ServerResponse.createBySuccess("修改商品销售状态成功");
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        /**
         * @Description //TODO 获取商品详情
           @Author Leo
         * @Date 20:51 2020/3/11
         * @Param [productId]
         * @return com.imooc.demo.common.ServerResponse
        */
        if(productId == null){
            //参数错误
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        //查询是否存在此商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse getProductListPageHelper(int pageNum, int pageSize) {
        /**
         * @Description //TODO 分页查询所有商品
           @Author Leo
         * @Date 22:46 2020/3/11
         * @Param [pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse
        */
        //startpage
        //填充自己的SQL查询逻辑
        //pageHelper收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.findAllProductList();
        if(productList.size() == 0){
            return ServerResponse.createByErrorMessage("服务器错误,抱歉");
        }
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVoList);
        //pageInfo根据productListVoList分页
        pageInfo.setList(productListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse searshProductListVoPage(Integer productId, String productName, int pageNum, int pageSize) {
        /**
         * @Description //TODO 根据姓名或productId进行搜素,分页显示
           @Author Leo
         * @Date 23:59 2020/3/11
         * @Param [productId, productName, pageNum, pageSize]
         * @return com.imooc.demo.common.ServerResponse
        */
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            //模糊查询
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //数据库查询的结果集合productList
        List<Product> productList = productMapper.selcetByNameAndProductId(productName,productId);
        log.info("1.result={}",productList);
        //存放最后结果的集合
        List<ProductListVo> productListVoList = Lists.newArrayList();
        //遍历转换
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        log.info("2.result={}",productListVoList);
        //PageInfo结果封装
        PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVoList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    @Override
    public ServerResponse searshProductListVo(String productName,Integer productId) {
        /**
         * @Description //TODO 根据姓名或productId进行搜素,不分页
           @Author Leo
         * @Date 7:29 2020/3/12
         * @Param [productId, productName]
         * @return com.imooc.demo.common.ServerResponse
        */
        //StringUtils.isNotBlank(productName) 等同于 productName !=null && productName !=""
//        if(StringUtils.isNotBlank(productName)){
//            productName = new StringBuilder().append("%").append(productName).append("%").toString();
//        }
        if(productName !=null || productName !=""){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }

        List<Product> productList = productMapper.selcetByNameAndProductId(productName,productId);
        log.info("productList={}",productList);
        List<ProductListVo> productListVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        log.info("productListVoList={}",productListVoList);
        return ServerResponse.createBySuccess(productListVoList);
    }

    //获取ProductListVo
    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setName(product.getName());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix",
                "http://img.happymmall.com/"));
        return productListVo;


    }

    //获取productDetailVo
    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());

        //ImageHost 获取网址前缀 ,网址
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix",
                "http://img.happymmall.com/"));
        //parentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //createTime
        productDetailVo.setCreateTime(new Date());
        productDetailVo.setUpdateTime(new Date());
        return productDetailVo;
    }


}