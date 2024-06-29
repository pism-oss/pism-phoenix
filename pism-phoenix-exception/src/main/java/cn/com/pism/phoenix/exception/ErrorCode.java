package cn.com.pism.phoenix.exception;

/**
 * @author perccyking
 * @since 24-06-28 19:25
 */
public interface ErrorCode {
    /**
     * <p>
     * 获取错误代码
     * </p>
     *
     * @return {@link Integer} 错误代码
     * @author wangyihuai
     * @since 2022/12/28 11:59
     */
    Integer getCode();

    /**
     * <p>
     * 获取错误信息
     * </p>
     *
     * @return {@link Integer} 错误信息
     * @author wangyihuai
     * @since 2022/12/28 11:59
     */
    String getMsg();
}
