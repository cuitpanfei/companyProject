package cn.com.pfinfo.demo.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 必须在前置注解ActionController使用的情况下才会生效，存在一下几个子注解：
 * <ul>
 *     <li>GetResolver</li>
 *     <li>PostResolver</li>
 *     <li>PutResolver</li>
 *     <li>DeleteResolver</li>
 * </ul>
 * @author panfei
 * @since  1.0
 * @version 1.0
 * @see ActionController
 * @see GetResolver
 * @see PostResolver
 * @see PutResolver
 * @see DeleteResolver
 */
@Component
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlResolver {
    String url() default "";
    RequestMethod[] method() default {};
}
