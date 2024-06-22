package cn.com.pism.phoenix.annotations.model;

/**
 * 错误码定义
 *
 * @author perccyking
 * @since 24-06-07 14:59
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMsg();
}
