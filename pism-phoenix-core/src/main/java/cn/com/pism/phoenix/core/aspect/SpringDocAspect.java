package cn.com.pism.phoenix.core.aspect;

import com.github.xiaoymin.knife4j.core.conf.ExtensionsConstants;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * springdoc 增强
 *
 * @author perccyking
 * @since 24-06-22 22:02
 */
@Aspect
@Component
public class SpringDocAspect {


    @Pointcut("execution(public * org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource.openapiJson(..))")
    public void multipleOpenApiWebMvcResource() {
        // do nothing
    }


    @Around("multipleOpenApiWebMvcResource()")
    public Object enhance(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Integer> groupSerial = HashMap.newHashMap(16);
        Map<String, Integer> groupPathSerial = HashMap.newHashMap(16);
        Set<String> groupSet = HashSet.newHashSet(16);

        Object result = joinPoint.proceed();
        OpenAPI openApi = Json.mapper().readValue((byte[]) result, OpenAPI.class);
        Paths paths = openApi.getPaths();
        paths.forEach((path, v) -> {
            for (Operation operation : v.readOperations()) {

                addGroupSerial(operation, groupSerial, groupSet);

                addPathSerial(operation, groupPathSerial);
            }
        });
        return Json.mapper().writeValueAsString(openApi).getBytes(StandardCharsets.UTF_8);
    }

    private void addPathSerial(Operation operation, Map<String, Integer> groupPathSerial) {
        Map<String, Object> extensions = operation.getExtensions();
        int serial = 0;
        if (MapUtils.isNotEmpty(extensions)) {
            serial = (int) extensions.getOrDefault(ExtensionsConstants.EXTENSION_ORDER, Integer.MAX_VALUE);
            if (Integer.MAX_VALUE == serial || Integer.MIN_VALUE == serial) {
                serial = getSerial(operation, serial, groupPathSerial, extensions);
            }
        } else {
            Map<String, Object> newExtensions = new HashMap<>();
            serial = getSerial(operation, serial, groupPathSerial, newExtensions);
            operation.setExtensions(newExtensions);
        }

        String itemName = StringUtils.firstNonBlank(operation.getSummary(), operation.getDescription(), operation.getOperationId());
        operation.setSummary(serial + "." + itemName);
    }

    private int getSerial(Operation operation, int serial, Map<String, Integer> groupPathSerial, Map<String, Object> newExtensions) {
        List<String> tags = operation.getTags();
        if (CollectionUtils.isNotEmpty(tags)) {
            String tag = tags.getFirst();
            serial = groupPathSerial.getOrDefault(tag, 0);
            groupPathSerial.put(tag, serial + 1);
            newExtensions.put(ExtensionsConstants.EXTENSION_ORDER, serial);
        }
        return serial;
    }

    private void addGroupSerial(Operation operation, Map<String, Integer> groupSerial, Set<String> groupSet) {
        List<String> tags = new ArrayList<>();
        for (String tag : operation.getTags()) {
            Integer orDefault = groupSerial.getOrDefault(tag, groupSet.size());
            groupSerial.put(tag, orDefault);
            groupSet.add(tag);
            tags.add(orDefault + "." + tag);
        }
        operation.setTags(tags);
    }
}
