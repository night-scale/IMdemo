package com.imdemo.codec;

import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.codec.interfaces.Encoder;
import com.imdemo.protocol.ProtocolBase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodecManager {
    private static final Map<Byte, Decoder<? extends ProtocolBase>> decoders = new ConcurrentHashMap<>();
    private static final Map<Byte, Encoder<? extends ProtocolBase>> encoders = new ConcurrentHashMap<>();


    public static void registerDecoder(byte type, Decoder<? extends ProtocolBase> decoder) {
        System.out.println("[CodecManager] register decoder type = " + type);
        decoders.put(type, decoder);
    }

    public static void registerEncoder(byte type, Encoder<? extends ProtocolBase> encoder) {
        System.out.println("[CodecManager] register encoder type = " + type);
        encoders.put(type, encoder);
    }

    public static Decoder<? extends ProtocolBase> getDecoder(byte type) {
        return decoders.get(type);
    }

    public static Encoder<? extends ProtocolBase> getEncoder(byte type) {
        return encoders.get(type);
    }

    public static void printAll(){
        System.out.println("Registered decoders:");
        decoders.forEach((type, decoder) -> System.out.println("Type: " + type + ", Decoder: " + decoder.getClass().getName()));

        System.out.println("Registered encoders:");
        encoders.forEach((type, encoder) -> System.out.println("Type: " + type + ", Encoder: " + encoder.getClass().getName()));
    }
}

