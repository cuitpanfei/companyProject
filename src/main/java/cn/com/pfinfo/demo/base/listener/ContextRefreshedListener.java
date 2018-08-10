package cn.com.pfinfo.demo.base.listener;

import cn.com.pfinfo.demo.annotation.*;
import cn.com.pfinfo.demo.base.cache.ControllerMapCache;
import cn.com.pfinfo.demo.util.NullUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author panfei
 * @since 1.0
 * @version 1.0
 */
@Slf4j
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(ActionController.class);
            if (NullUtil.isNotEmpty(beans)) {
                beans.forEach((key, value) -> {
                    UrlResolver urlAnnnotation = AnnotationUtils.findAnnotation(value.getClass(), UrlResolver.class);
                    String baseUrl = urlAnnnotation == null ? "" : urlAnnnotation.url();
                    Method[] all = ReflectionUtils.getAllDeclaredMethods(value.getClass());
                    for (Method method : all) {
                        addCacheInfo(value, method, baseUrl);
                    }
                });
            }
        }
    }

    /**
     * 获取方法级别的UrlResolver注解并加入bean以及method信息到自定义容器
     * @param bean
     * @param method
     * @param baseUrl
     */
    private void addCacheInfo(@NotNull Object bean, @NotNull Method method, String baseUrl) {
        UrlResolver urlAnnnotation = AnnotatedElementUtils.findMergedAnnotation(method, UrlResolver.class);
        if(NullUtil.isEmpty(urlAnnnotation)){
            return;
        }
        if (!urlAnnnotation.url().isEmpty()) {
            baseUrl += urlAnnnotation.url();
        }
        if (NullUtil.isEmpty(baseUrl)) {
            baseUrl+=method.getName();
        }
        for (RequestMethod methodType : urlAnnnotation.method()) {
            String key = baseUrl + "#" + methodType.name();
            if (ControllerMapCache.getInstance().getCache().containsKey(key)) {
                String msg = String.format("method %s 上的注解路径 %s [%s]已存在，不能添加到映射！！！",method.getName(),urlAnnnotation.url(),methodType.name());
                throw new IllegalStateException(msg);
            }
            Map<String, Object> map = new HashMap<>(2);
            map.put(ControllerMapCache.Constant.BEAN, bean);
            map.put(ControllerMapCache.Constant.METHOD, method);
            ControllerMapCache.getInstance().putCache(baseUrl + "#" + methodType.name(), map);
        }
    }
}
