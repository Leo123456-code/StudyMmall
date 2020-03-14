package com.imooc.demo.web.portal;

import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.service.IProductService;
import com.imooc.demo.vo.ProductDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ProductController
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/13-6:01
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    //获取商品详情
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
       return productService.getProductDetailVo(productId);
    }

    //前端搜素结果展示


}
