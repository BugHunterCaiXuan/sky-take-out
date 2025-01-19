package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.sky.enumeration.OperationType;
/**
 * 自定义的注解，用于标识某个方法需要进行功能字段自动填充
 */
@Target({ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //定义一个枚举类型，用于指定操作类型，INSERT、UPDATE
    OperationType value();
}
