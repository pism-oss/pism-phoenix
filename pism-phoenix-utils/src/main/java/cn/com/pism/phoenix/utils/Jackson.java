package cn.com.pism.phoenix.utils;

import cn.com.pism.phoenix.utils.exception.PmnxException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author perccyking
 * @since 24-06-03 18:54
 */
@Slf4j
public class Jackson {

    private Jackson() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 添加了部分配置信息
     */
    private static final ObjectMapper OBJECT_MAPPER1 = new ObjectMapper();

    static {
        //序列化设置非空选项
        OBJECT_MAPPER1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 默认实例
     *
     * @return 实例
     */
    public static ObjectMapper instance() {
        return OBJECT_MAPPER;
    }

    /**
     * 添加了部分配置的实例
     *
     * @return 实例
     * @see JsonInclude.Include#NON_NULL
     */
    public static ObjectMapper instance1() {
        return OBJECT_MAPPER1;
    }

    /**
     * <p>
     * 将对象转换为json字符串
     * </p>
     * by perccyking
     *
     * @param object : 对象
     * @return {@link String} 字符串
     * @since 24-06-22 18:27
     */
    public static String toJsonString(Object object) {
        try {
            return instance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new PmnxException(e);
        }
    }

    /**
     * <p>
     * 将对象转换为json字符串，不包含空属性
     * </p>
     * by perccyking
     *
     * @param object : 对象
     * @return {@link String} 不包含空属性的json字符串
     * @since 24-06-22 18:28
     */
    public static String toJsonStringNonNull(Object object) {
        try {
            return instance1().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new PmnxException(e);
        }
    }

    /**
     * <p>
     * 将json字符串转换为对象列表
     * </p>
     * by perccyking
     *
     * @param json  : json 字符串
     * @param clazz : 对象类型
     * @return {@link List<T>} 对象列表
     * @since 24-06-22 18:28
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return instance().readValue(json, instance().getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new PmnxException(e);
        }
    }

}
