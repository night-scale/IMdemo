package com.imdemo.codec;

import com.imdemo.codec.annotation.MessageDecoder;
import com.imdemo.codec.annotation.MessageEncoder;
import com.imdemo.codec.interfaces.Decoder;
import com.imdemo.codec.interfaces.Encoder;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.Set;

public class CodecAutoRegistrar {
    public static void registerAllCodecs(String decPackageName, String encPackageName) {
        System.out.println("[function] registerAllCodecs");

        Reflections decReflections = new Reflections(decPackageName, Scanners.TypesAnnotated);
        Reflections encReflections = new Reflections(encPackageName, Scanners.TypesAnnotated);

        Set<Class<?>> annotatedClasses = decReflections.getTypesAnnotatedWith(MessageDecoder.class);
        for (Class<?> clazz : annotatedClasses) {
            if (Decoder.class.isAssignableFrom(clazz)) {
                try {
                    MessageDecoder annotation = clazz.getAnnotation(MessageDecoder.class);
                    Decoder<?> decoder = (Decoder<?>) clazz.getDeclaredConstructor().newInstance();
                    CodecManager.registerDecoder(annotation.type(), decoder);
                } catch (Exception e) {
                    System.err.println("Failed to register decoder for class: " + clazz.getName());
                }
            }
        }

        for (Class<?> clazz : encReflections.getTypesAnnotatedWith(MessageEncoder.class)) {
            if (Encoder.class.isAssignableFrom(clazz)) {
                try {
                    MessageEncoder annotation = clazz.getAnnotation(MessageEncoder.class);
                    Encoder<?> encoder = (Encoder<?>) clazz.getDeclaredConstructor().newInstance();
                    CodecManager.registerEncoder(annotation.type(), encoder);
                } catch (Exception e) {
                    System.err.println("Failed to register encoder for class: " + clazz.getName());
                }
            }
        }
        System.out.println("[function] registerAllcodecs ended");

    }
}

