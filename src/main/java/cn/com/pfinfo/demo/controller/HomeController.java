package cn.com.pfinfo.demo.controller;

import cn.com.pfinfo.demo.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author panfei
 * @since 1.0
 * @version 1.0
 */
@ActionController
public class HomeController {

    @UrlResolver(url = "/iot/index",method = {RequestMethod.POST})
    public String index(){
        return "index";
    }
    @DeleteResolver(url = "/iot/del")
    public String del(){
        return "del";
    }
    @GetResolver(url = "/iot/index")
    public String testIndex(){
        return "del";
    }
    @PutResolver(url = "/iot/put")
    public String put(){
        return "put";
    }
    @GetResolver(url = "/iot/get")
    public String get(){
        return "get";
    }
    @PostResolver(url = "/iot/post")
    public String post(){
        return "post";
    }
}
