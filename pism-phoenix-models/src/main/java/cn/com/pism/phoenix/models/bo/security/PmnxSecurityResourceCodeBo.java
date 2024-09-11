package cn.com.pism.phoenix.models.bo.security;

import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-09 21:16
 */
@Data
public class PmnxSecurityResourceCodeBo {

    /**
     * 资源
     */
    private String resource;

    /**
     * url对应权限代码
     */
    private String code;
}
