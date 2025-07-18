package test.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * <pre>
 * Created on 2025/7/10 上午11:59
 * by @author WeiBoWen
 * </pre>
 */
public class MyKryoUtil {

    // 使用 ThreadLocal 保证线程安全
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false); // 不需要注册类（适合快速开发）
        return kryo;
    });

    /**
     * 序列化任意对象为 byte[]
     */
    public static byte[] serialize(Object obj, boolean references) {
        Kryo kryo = kryoThreadLocal.get();
        // 关键：启用对象引用追踪
        kryo.setReferences(references);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        // 支持任意对象
        kryo.writeClassAndObject(output, obj);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 序列化任意对象为 byte[]
     */
    public static byte[] serialize(Object obj) {
        return serialize(obj, false);
    }

    /**
     * 反序列化 byte[] 为任意类型对象（无需指定类型，但需确保类型一致）
     */
    public static <T> T deserializeObject(byte[] bytes, Class<T> clazz) {
        Kryo kryo = kryoThreadLocal.get();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        Object obj = kryo.readClassAndObject(input);
        input.close();
        return (T) obj;
    }

}
