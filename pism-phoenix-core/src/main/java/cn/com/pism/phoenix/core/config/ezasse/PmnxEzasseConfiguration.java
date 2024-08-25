package cn.com.pism.phoenix.core.config.ezasse;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.starter.EzasseConfigPostProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author perccyking
 * @since 24-06-29 18:08
 */
@Configuration
public class PmnxEzasseConfiguration implements EzasseConfigPostProcessor {

    private static final List<String> PMNX_DEFAULT_GROUP = Collections.singletonList("pmnx_core");


    @Override
    public void postProcessAfterInitialization(EzasseConfig ezasseConfig) {
        List<String> groupOrder = ezasseConfig.getGroupOrder();
        if (CollectionUtils.isNotEmpty(groupOrder)) {
            ArrayList<String> newGroup = new ArrayList<>();
            newGroup.addAll(PMNX_DEFAULT_GROUP);
            newGroup.addAll(groupOrder);
            ezasseConfig.setGroupOrder(newGroup);
        } else {
            ezasseConfig.setGroupOrder(PMNX_DEFAULT_GROUP);
        }
    }
}
