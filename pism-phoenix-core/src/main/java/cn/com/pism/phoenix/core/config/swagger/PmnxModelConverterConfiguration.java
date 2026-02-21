package cn.com.pism.phoenix.core.config.swagger;

import cn.com.pism.phoenix.core.config.swagger.converter.PmnxModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author perccyking
 * @since 26-02-21 13:41
 */
@Configuration
@RequiredArgsConstructor
public class PmnxModelConverterConfiguration {

    private final List<PmnxModelConverter> modelConverters;

    @PostConstruct
    public void registerModelConverter() {
        if (CollectionUtils.isEmpty(modelConverters)) {
            return;
        }
        modelConverters.forEach(converter -> ModelConverters.getInstance().addConverter(converter));

    }
}
