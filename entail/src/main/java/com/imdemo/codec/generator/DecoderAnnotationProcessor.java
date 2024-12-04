package com.imdemo.codec.generator;

import com.imdemo.codec.annotation.DecoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.Console;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"ProtocolMessage", "DecoderGenerate"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DecoderAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 查找所有标记了 @DecoderGenerate 的类
        System.out.println("DecoderAnnotationProcessor is running...");
        for (Element element : roundEnv.getElementsAnnotatedWith(DecoderGenerate.class)) {
            TypeElement typeElement = (TypeElement) element;
            DecoderGenerate decoderAnnotation = typeElement.getAnnotation(DecoderGenerate.class);

            // 获取目标解码器的类名和包名
            String className = typeElement.getSimpleName() + "DataDec";
            String packageName = "com.imdemo.codec.decoder";
            //processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
            int typeValue = decoderAnnotation.type();

            // 调用生成方法生成解码器类
            generateDataDecoderClass(packageName, className, typeElement, typeValue);
        }
        return true;
    }

    private void generateDataDecoderClass(String packageName, String className, TypeElement typeElement, int typeValue) {
        try {
            // 创建新的 Java 源文件
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + className);
            try (Writer writer = builderFile.openWriter()) {
                writer.write("package " + packageName + ";\n\n");
                writer.write("import io.netty.buffer.ByteBuf;\n");
                writer.write("import java.nio.charset.StandardCharsets;\n\n");
                writer.write("import com.imdemo.codec.annotation.MessageDecoder");

                writer.write("@MessageDecoder(type = 0x" + Integer.toHexString(typeValue) + ")\n");
                writer.write("public class " + className + " implements Decoder<" + typeElement.getSimpleName() + "> {\n");

                // 生成 decode 方法
                writer.write("    @Override\n");
                writer.write("    public void decode(ByteBuf in, " + typeElement.getSimpleName() + " message) throws Exception {\n");

                // 遍历数据字段并生成相应解码逻辑
                List<? extends Element> fields = typeElement.getEnclosedElements();
                for (Element field : fields) {
                    ProtocolData protocolData = field.getAnnotation(ProtocolData.class);
                    if (protocolData != null) {
                        String fieldName = field.getSimpleName().toString();
                        Class<?> fieldType = protocolData.dataType();

                        // 生成字段解码逻辑
                        if (fieldType == int.class) {
                            writer.write("        message.set" + capitalize(fieldName) + "(in.readInt());\n");
                        } else if (fieldType == long.class) {
                            writer.write("        message.set" + capitalize(fieldName) + "(in.readLong());\n");
                        } else if (fieldType == String.class) {
                            writer.write("        message.set" + capitalize(fieldName) + "(readString(in));\n");
                        }
                    }
                }
                writer.write("    }\n");
                writer.write("}\n"); // 结束解码器类
                System.out.println(className + ".java Generated!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 辅助方法：首字母大写
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

