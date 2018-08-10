package cn.com.pfinfo.demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在使用了{@link ActionController cn.com.pfinfo.demo.annotation.ActionController}的bean的内部方法上生效。
 * eg:
 *
 * @author panfei
 * @since 1.0
 * @version 1.0
 * @see ActionController
 * @see UrlResolver
 */
@Component
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UrlResolver(
        method = RequestMethod.GET
)
public @interface GetResolver {
    @AliasFor(attribute = "url",annotation = UrlResolver.class)
    String url() default "";
}
