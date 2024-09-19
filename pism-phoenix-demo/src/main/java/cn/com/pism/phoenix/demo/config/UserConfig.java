package cn.com.pism.phoenix.demo.config;

import cn.com.pism.phoenix.annotations.form.Form;
import cn.com.pism.phoenix.annotations.form.FormField;
import lombok.Data;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-13 18:45
 */
@Form(name = "用户配置")
@Data
public class UserConfig {

    /**
     * 用户xx
     */
    @FormField(label = "用户xx")
    private List<String> xx;
}
