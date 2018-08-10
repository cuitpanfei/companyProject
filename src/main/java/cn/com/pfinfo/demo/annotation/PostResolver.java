package cn.com.pfinfo.demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UrlResolver(
        method = RequestMethod.POST
)
public @interface PostResolver {
    @AliasFor(attribute = "url",annotation = UrlResolver.class)
    String url() default "";

}
