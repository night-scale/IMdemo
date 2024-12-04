package com.imdemo.codec.generator;

import com.imdemo.codec.annotation.EncoderGenerate;
import com.imdemo.codec.annotation.ProtocolData;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;


@SupportedAnnotationTypes({"ProtocolMessage", "DecoderGenerate"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EncoderAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(EncoderGenerate.class)) {
            TypeElement typeElement = (TypeElement) element;
            EncoderGenerate decoderAnnotation = typeElement.getAnnotation(EncoderGenerate.class);

            String className = typeElement.getSimpleName() + "DataEnc";
            String packageName = "com.imdemo.codec.encoder";
            int typeValue = decoderAnnotation.type();

            generateDataEncoderClass(packageName, className, typeElement, typeValue);
        }
        return true;
    }

    private void generateDataEncoderClass(String packageName, String className, TypeElement typeElement, int typeValue) {
        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + className);
            try (Writer writer = builderFile.openWriter()) {
                // 包和导入语句
                writer.write("package " + packageName + ";\n\n");
                writer.write("import io.netty.buffer.ByteBuf;\n");
                writer.write("import java.nio.charset.StandardCharsets;\n\n");
                writer.write("import com.imdemo.codec.annotation.MessageEncoder;\n");
                writer.write("import com.imdemo.codec.annotation.ProtocolData;\n");
                writer.write("import com.imdemo.codec.encoder.Encoder;\n\n");

                // 编码器类注解和声明
                writer.write("@MessageEncoder(type = 0x" + Integer.toHexString(typeValue) + ")\n");
                writer.write("public class " + className + " implements Encoder<" + typeElement.getSimpleName() + "> {\n");

                // encode 方法定义
                writer.write("    @Override\n");
                writer.write("    public void encode(ByteBuf out, " + typeElement.getSimpleName() + " message) throws Exception {\n");

                // 直接写入字段数据
                List<? extends Element> fields = typeElement.getEnclosedElements();
                for (Element field : fields) {
                    ProtocolData protocolData = field.getAnnotation(ProtocolData.class);
                    if (protocolData != null) {
                        String fieldName = field.getSimpleName().toString();
                        String fieldType = field.asType().toString();

                        if (fieldType.equals("int")) {
                            writer.write("        out.writeInt(message." + fieldName + ");\n");
                        } else if (fieldType.equals("long")) {
                            writer.write("        out.writeLong(message." + fieldName + ");\n");
                        } else if (fieldType.equals("java.lang.String")) {
                            writer.write("        byte[] " + fieldName + "Bytes = message." + fieldName + ".getBytes(StandardCharsets.UTF_8);\n");
                            writer.write("        out.writeInt(" + fieldName + "Bytes.length);\n");
                            writer.write("        out.writeBytes(" + fieldName + "Bytes);\n");
                        }
                    }
                }

                // 结束 encode 方法和类
                writer.write("    }\n");
                writer.write("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
