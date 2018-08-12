package cn.com.pfinfo.demo.base;

import com.alibaba.fastjson.JSONObject;

public class BaseController implements ControllerHandler {
    public BaseController() {
    }

    @Override
    public void invoke(JSONObject msg) {
        System.out.println(msg.toJSONString());
    }
}
