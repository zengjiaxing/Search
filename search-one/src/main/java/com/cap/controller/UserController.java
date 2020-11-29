package com.cap.controller;

import com.cap.mapper.UserMapper;
import com.cap.pojo.CollectionData;
import com.cap.pojo.Record;
import com.cap.pojo.User;
import com.cap.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;//用户相关数据库操作接口
    @Autowired
    InfoService infoService;//es操作相关封装类
    @GetMapping("/test")//用来测试的，无视就好
    public int test(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        return 1;
    }
    /**
     * 登出，将session中的user对象置为null
     * @return int 如果已有登入则返回1，否则返回0
     * */
    @GetMapping("/logOut")
    public int logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            session.setAttribute("user",null);//在session中设置用户对象为空
            return 1;
        }
        return 0;
    }
    /**
     * 判断是否为登录状态
     * @return int 是则返回1，否则返回0
     * */
    @GetMapping("/querySign")
    public int querySign(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        if (session.getAttribute("user") != null){//判断是否有用户登录
            return 1;
        }else {
            return 0;
        }
    }
    /**
     * 登录，判断传入的账号密码和数据库是否匹配
     * @param name 账号
     * @param password 密码
     * @return int 匹配返回1，否则返回0
     * */
    @GetMapping("/queryUser/{name}/{password}")
    public int queryUser(HttpServletRequest request, @PathVariable("name") String name, @PathVariable("password") String password){
        HttpSession session = request.getSession();
        User user = userMapper.queryUser(name);
        System.out.println(session.getId());
        if (user!=null && user.getPassword().equals(password)){//对比密码
            session.setAttribute("user",user);//在session中设置用户对象为当前登录对象
            return 1;
        }else {
            return 0;
        }
    }
    /**
     * 创建账号，在数据库中插入传入的账号密码
     * @param name 账号
     * @param password 密码
     * @return int 插入成功返回1，否则返回0
     * */
    @GetMapping("/addUser/{name}/{password}")
    public int addUser(HttpServletRequest request, @PathVariable("name") String name,@PathVariable("password") String password){
        HttpSession session = request.getSession();
        User user = new User(1,name,password);
        int sign = userMapper.addUser(user);//在数据库中插入用户
        User userZero = userMapper.queryUser(name);
        if (sign == 1){
            session.setAttribute("user",userZero);//在session中设置用户对象为当前插入对象
            return 1;
        }
        return 0;
    }
    /**
     * 添加浏览记录
     * @param dataId 需要记录的词条id
     * @return int 成功插入返回1，否则返回0
     * */
    @GetMapping("/addRecord/{dataId}")
    public int addRecord(HttpServletRequest request,@PathVariable("dataId") String dataId){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(date);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");//获取用户对象
        if(user!=null){//如果有已登录的对象
            Record record = userMapper.queryRecord(new Record(1,user.getUserId(),dataId,datetime));
            System.out.println(record);
            if (record != null){//记录已存在则更新
                int i = userMapper.updateRecord(new Record(1,user.getUserId(),dataId,datetime));
                return i;
            }else{//记录不存在则添加
                int sign = userMapper.addRecord(new Record(1,user.getUserId(),dataId,datetime));
                return sign;
            }
        }
        return 0;
    }
    /**
     * 获取浏览记录
     * @return String 浏览记录的数据
     * */
    @GetMapping("/getRecord")
    public String getRecord(HttpServletRequest request) throws IOException {
        List<String> list = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");//获取登录的用户
        if (user != null){
            list = userMapper.queryRecordByUserId(user.getUserId());//根据用户获取记录id集合
            /*for(int i = 0; i < list.size(); i++){
                System.out.println(list.get(i));
            }*/
        }
        String json = infoService.searchById(list);//使用组员封装的es api，通过id集合获取对应的词条文档
        return json;
    }
    /**
     * 获取收藏的词条数据
     * @return String 收藏的词条数据
     * */
    @GetMapping("/getCollection")
    public String getCollection(HttpServletRequest request) throws IOException
    {
        List<String> list = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");//获取登录的用户
        if (user != null){
            list = userMapper.queryCollectionByUserId(user.getUserId());//根据用户收藏记录id集合
            System.out.println(list);
        }
        String json = infoService.searchById(list);//使用组员封装的es api，通过id集合获取对应的词条文档
        return json;
    }
    /**
     * 添加收藏词条
     * @param dataId 需要收藏的词条id
     * @return int 成功返回1，否则返回0
     * */
    @GetMapping("/addCollection/{dataId}")
    public int addCollection(HttpServletRequest request,@PathVariable("dataId") String dataId) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(date);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");//获取登录的用户
        if(user!=null){
            CollectionData collectionData = userMapper.queryCollection(new CollectionData(1,user.getUserId(),dataId,datetime));//判断该记录是否已被收藏
            if (collectionData != null){
                System.out.println("收藏对象已存在");
            }else{
                int sign = userMapper.addCollection(new CollectionData(1,user.getUserId(),dataId,datetime));//添加收藏记录
                return sign;
            }
        }
        return 0;
    }
    /**
     * 取消收藏
     * @param dataId 需要收藏的词条id
     * @return int 成功返回1，否则返回0
     * */
    @GetMapping("/deleteCollection/{dataId}")
    public int deleteCollection(HttpServletRequest request,@PathVariable("dataId") String dataId) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(date);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");//获取登录的用户
        if(user!=null){
            CollectionData collectionData = userMapper.queryCollection(new CollectionData(1,user.getUserId(),dataId,datetime));//判断是否收藏了该词条
            if (collectionData != null){
                int sign = userMapper.deleteCollection(new CollectionData(1,user.getUserId(),dataId,datetime));//在收藏中删去该词条
                return sign;
            }else{
                System.out.println("收藏对象不存在");
                return 0;
            }
        }
        return 0;
    }
    /**
     * 判断词条是否被收藏
     * @param dataId 需要收藏的词条id
     * @return int 被收藏返回1，否则返回0
     * */
    @GetMapping("/queryCollection/{dataId}")
    public int queryCollection(HttpServletRequest request,@PathVariable("dataId") String dataId) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(date);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");//获取登录的用户对象
        if(user!=null){
            CollectionData collectionData = userMapper.queryCollection(new CollectionData(1,user.getUserId(),dataId,datetime));//判断该词条是否被收藏
            System.out.println(collectionData);
            if(collectionData!=null){
                return 1;
            }
        }
        return 0;
    }
}
