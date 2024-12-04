package com.imdemo.protocol;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProtocolMessage {
    byte type();  // 消息类型
    String description() default "";  // 描述
}