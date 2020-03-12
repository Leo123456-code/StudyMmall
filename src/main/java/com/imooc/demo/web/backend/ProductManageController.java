package com.imooc.demo.web.backend;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.Product;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IProductService;
import com.imooc.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName: ProductManageController
 * Description: //TODO商品控制层
 * Author: Leo
 * Date: 2020/3/11-7:05
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;

    //新增或修改商品
    @RequestMapping("save.do")
    public ServerResponse<Product> productSave(HttpSession session,Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充增加商品的业务逻辑
            return productService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //修改商品的销售状态
    @RequestMapping("set_sale_staus.do")
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //修改商品的销售状态
            return productService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //获取商品详情
    @RequestMapping("get_detail.do")
    public ServerResponse getDetail(HttpSession session,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //获取商品详情
            return productService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }

    //分页
    @RequestMapping("get_List.do")
    public ServerResponse getListPageHelper(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1")  int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //查询所有商品并进行分页
            return productService.getProductListPageHelper(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }
    //根据姓名或productId进行搜素,分页
    @RequestMapping("search_product_page.do")
    public ServerResponse searchProductVOListPage(HttpSession session,Integer productId,String productName,@RequestParam(value = "pageNum",defaultValue = "1")  int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //根据姓名或productId进行搜素
            return productService.searshProductListVoPage(productId,productName,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }
    //根据姓名或productId进行搜素,不分页
    @RequestMapping("search_product.do")
    public ServerResponse searchProductVOListPage(HttpSession session,String productName,Integer productId){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //根据姓名或productId进行搜素
            return productService.searshProductListVo(productName,productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员身份");
        }
    }


}
