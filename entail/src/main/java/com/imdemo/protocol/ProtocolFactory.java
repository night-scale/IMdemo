package com.imdemo.protocol;

import com.imdemo.protocol.request.LoginRequest;
import com.imdemo.protocol.response.LoginResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ProtocolFactory {

    // 保存消息类型与消息类之间的映射
    private static final Map<Byte, Class<? extends ProtocolBase>> messageRegistry = new HashMap<>();

    static {
        registerMessages(LoginRequest.class, LoginResponse.class);  // 可注册更多类型
    }

    // 注册消息类
    private static void registerMessages(Class<? extends ProtocolBase>... messageClasses) {
        for (Class<? extends ProtocolBase> messageClass : messageClasses) {
            if (messageClass.isAnnotationPresent(ProtocolMessage.class)) {
                ProtocolMessage annotation = messageClass.getAnnotation(ProtocolMessage.class);
                messageRegistry.put(annotation.type(), messageClass);
            }
        }
    }

    // 根据消息类型创建消息实例
    public static ProtocolBase createMessage(int length, byte messageType, long sequenceId, Object... params) {
        System.out.println("[createMessage] " + messageType);
        Class<? extends ProtocolBase> messageClass = messageRegistry.get(messageType);
        if (messageClass != null) {
            try {
                byte status = Byte.MAX_VALUE;
                if (params != null && params.length > 0) {
                    status = (byte) params[0];
                }
                return messageClass.getConstructor(int.class, long.class, byte.class).newInstance(length, sequenceId, status);
            } catch (Exception e) {
                System.err.println("Error creating message: " + e.getMessage());
                return null; // 或者返回一个默认的消息对象
            }
        } else {
            throw new IllegalArgumentException("Unsupported message type: " + messageType);
        }
    }
}