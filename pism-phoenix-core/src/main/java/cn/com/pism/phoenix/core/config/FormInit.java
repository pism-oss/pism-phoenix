package cn.com.pism.phoenix.core.config;

import cn.com.pism.phoenix.core.service.PmnxFormService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author perccyking
 * @since 24-09-13 20:15
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class FormInit {

    private final PmnxFormService pmnxFormService;

    @PostConstruct
    public void pmnxFormInit() {
        //为表单赋值一次
        pmnxFormService.refreshForm();

    }
}
