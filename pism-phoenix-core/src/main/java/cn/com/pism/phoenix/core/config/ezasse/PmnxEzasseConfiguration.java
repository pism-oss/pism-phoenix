package cn.com.pism.phoenix.core.config.ezasse;

import cn.com.pism.ezasse.manager.ConfigManager;
import cn.com.pism.ezasse.model.EzasseConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author perccyking
 * @since 24-06-29 18:08
 */
@Configuration
public class PmnxEzasseConfiguration implements BeanPostProcessor {

    private static final List<String> PMNX_DEFAULT_GROUP = Collections.singletonList("pmnx_core");

    @Override
    public @Nullable Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof ConfigManager configManager){
            EzasseConfig config = configManager.getConfig();
            List<String> groupOrder = config.getGroupOrder();
            if (CollectionUtils.isNotEmpty(groupOrder)) {
                ArrayList<String> newGroup = new ArrayList<>();
                newGroup.addAll(PMNX_DEFAULT_GROUP);
                newGroup.addAll(groupOrder);
                config.setGroupOrder(newGroup);
            } else {
                config.setGroupOrder(PMNX_DEFAULT_GROUP);
            }
            configManager.setConfig(config);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
