package cn.com.pfinfo.demo.base.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author panfei
 * @since 1.0
 * @version 1.0
 */
@Slf4j
public class ControllerMapCache {

    private static ControllerMapCache instance = null;

    private Map<String, Map<String, Object>> cache = new HashMap<>(64);

    public static ControllerMapCache getInstance() {
        if (instance == null) {
            instance = new ControllerMapCache();
        }
        return instance;
    }

    public Map<String, Map<String, Object>> getCache() {
        return cache;
    }

    public void putCache(String key, Map<String, Object> value) {
        if(log.isDebugEnabled()){
            log.debug("put %s 到映射中",key);
        }
        cache.put(key, value);
    }

    public void setCache(Map<String, Map<String, Object>> cache) {
        this.cache = cache;
    }

    public static final class Constant {
        public static final String BEAN = "bean";
        public static final String METHOD = "method";
    }
}
