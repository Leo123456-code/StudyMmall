package com.imooc.demo.web.portal;

import com.imooc.demo.common.Const;
import com.imooc.demo.common.ResponseCode;
import com.imooc.demo.common.ServerResponse;
import com.imooc.demo.pojo.User;
import com.imooc.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * ClassName: UserController
 * Description: TODO
 * Author: Leo
 * Date: 2020/3/9-22:13
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    public ServerResponse<User> login(String username,String password,HttpSession session){
        /**
         * @Description //TODO 登录
           @Author Leo
         * @Date 23:41 2020/3/9
         * @Param [username, password, session]
         * @return com.imooc.demo.common.ServerResponse<com.imooc.demo.pojo.User>
        */
        ServerResponse<User> response = userService.login(username, password);
        if(response == null){
            return ServerResponse.createByErrorMessage("用户姓名或者密码错误");
        }
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session){
        /**
         * @Description //TODO 登出
           @Author Leo
         * @Date 23:44 2020/3/9
         * @Param [session]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess("登出成功");
    }


    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServerResponse<String> register(@RequestBody User user){
        /**
         * @Description //TODO 注册
           @Author Leo
         * @Date 23:46 2020/3/9
         * @Param [user]
         * @return com.imooc.demo.common.ServerResponse<java.lang.String>
        */
        ServerResponse<String> response = userService.reigster(user);
        return response;
    }

    //校验接口传入参数
    @RequestMapping(value = "check_vaild.do",method = RequestMethod.GET)
    public ServerResponse<String> checkVaild(String str,String type){

        return userService.checkVaild(str,type);
    }

    //获取用户登录信息
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        //成功
        return ServerResponse.createBySuccess(user);
    }

    //将问题返回给前端
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(String username,String email){
        return userService.forgetGetQuestion(username,email);

    }

    //找回密码答案
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
       return userService.selectAnswer(username,question,answer);

    }

    //重置密码
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
       return userService.resetAnswer(username,passwordNew,forgetToken);
    }

    //登錄狀態重置密碼
    @RequestMapping(value = "login_reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> loginResetPassword(HttpSession session,String passwordNew,
                                                     String passwordOld){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("未登录");
        }
        return userService.loginResetPassword(passwordNew,passwordOld,user);
    }

    //更新用户信息
    @RequestMapping(value = "update_information.do",method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }

        return response;
    }

    //获取用户登录状态信息
    @RequestMapping(value = "get_information.do",method = RequestMethod.GET)
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "未登录,需要登录status=10");
        }
        return userService.getInformation(user.getId());


    }




}
