package com.imdemo.protocol.request;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;
import com.imdemo.protocol.ProtocolBase;
import com.imdemo.protocol.ProtocolMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProtocolMessage(type = 0x02, description = "Login Request")
@DecoderGenerate(type = 0x02)
public class LoginRequest extends ProtocolBase {
    @ProtocolData(order = 1, dataType = int.class)
    private int id;
    @ProtocolData(order = 2, dataType = String.class)
    private String password;

    public LoginRequest(int length, long sequenceId, byte status) {
        super(length, (byte)0x02, sequenceId);  // 设置消息类型为 0x01
    }

    @Override
    public void process() {
        // 处理登录请求的业务逻辑
        System.out.println("Processing Login Request for user: " + id);
    }
}
