package cn.com.pism.phoenix.models;

import cn.com.pism.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-08-26 15:36
 */
@Schema(description = "通用返回对象")
@Data
public class JsonResult<T> {


    public static final int SUCCESS_FLAG = 1;

    public static final int FAILED_FLAG = 0;
    /**
     * 状态：0：失败，1：成功
     */
    @Schema(description = "状态：0：失败，1：成功")
    private int code;

    /**
     * 消息
     */
    @Schema(description = "消息")
    private String msg;

    /**
     * 数据
     */
    @Schema(description = "数据")
    private T data;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private Long ts;

    /**
     * 耗时
     */
    @Schema(description = "耗时")
    private Long ds;


    /**
     * 是否成功
     */
    @Schema(description = "是否成功")
    private boolean success;

    public boolean isSuccess() {
        //如果code不等于1 都为false
        return code == SUCCESS_FLAG;
    }

    /**
     * <p>
     * 通用响应
     * </p>
     *
     * @param code : 状态
     * @param msg  : 消息
     * @param data : 响应的数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> response(int code, String msg, T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setMsg(msg);
        result.setCode(code);
        result.setData(data);
        result.setTs(System.currentTimeMillis());
        return result;
    }

    /**
     * <p>
     * 成功响应
     * </p>
     *
     * @param msg  : 消息
     * @param data : 响应的数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> success(String msg, T data) {
        return response(SUCCESS_FLAG, msg, data);
    }

    /**
     * <p>
     * 响应成功的小
     * </p>
     *
     * @param msg : 消息
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> success(String msg) {
        return success(msg, null);
    }

    /**
     * <p>
     * 响应成功的数据
     * </p>
     *
     * @param data : 结果数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> successData(T data) {
        JsonResult<T> success = success();
        success.setData(data);
        return success;
    }

    /**
     * <p>
     * 操作成功响应
     * </p>
     *
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> success() {
        return success("操作成功");
    }

    /**
     * <p>
     * 失败响应
     * </p>
     *
     * @param msg  : 消息
     * @param data : 响应的数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failed(String msg, T data) {
        return response(FAILED_FLAG, msg, data);
    }

    /**
     * <p>
     * 响应失败的消息
     * </p>
     *
     * @param msg : 消息
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failed(String msg) {
        return failed(msg, null);
    }

    /**
     * <p>
     * 响应失败的数据
     * </p>
     *
     * @param data : 结果数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failedData(T data) {
        JsonResult<T> failed = failed();
        failed.setData(data);
        return failed;
    }

    /**
     * <p>
     * 操作失败响应
     * </p>
     *
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failed() {
        return failed("操作失败");
    }

    /**
     * <p>
     * 操作失败异常
     * </p>
     *
     * @param errorCode : 错误码枚举
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failed(ErrorCode errorCode) {
        JsonResult<T> result = new JsonResult<>();
        result.setMsg(errorCode.getMsg());
        result.setCode(errorCode.getCode());
        result.setTs(System.currentTimeMillis());
        return result;
    }

    /**
     * <p>
     * 操作失败响应
     * </p>
     *
     * @param errorCode : 错误码枚举
     * @param data      : 错误数据
     * @return {@link JsonResult<T>}
     * @since 24-08-26 16:29
     */
    public static <T> JsonResult<T> failedData(ErrorCode errorCode, T data) {
        JsonResult<T> failed = failed(errorCode);
        failed.setData(data);
        return failed;
    }
}
