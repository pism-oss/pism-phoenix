package cn.com.pism.phoenix.core.config.swagger.converter;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Iterator;

/**
 * @author perccyking
 * @since 26-02-21 14:06
 */
public interface PmnxModelConverter extends ModelConverter {

    default Schema<?> next(AnnotatedType type,
                           ModelConverterContext context,
                           Iterator<ModelConverter> chain) {
        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }
}
