package cn.com.pism.phoenix.demo;

import cn.hutool.core.util.RandomUtil;
import io.micrometer.core.instrument.binder.cache.EhCache2Metrics;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

/**
 * @author perccyking
 * @since 24-09-24 16:51
 */
public class PackageTest extends Object{
    public static void main(String[] args) {

        BigDecimal total = new BigDecimal("100");
        int num = 10;

        for (int i = 0; i < num; i++) {
            BigDecimal max = total.divide(BigDecimal.valueOf(num)).multiply(BigDecimal.valueOf(2));
            System.out.println(max);
            //随机生成金额
            BigDecimal current = RandomUtil.randomBigDecimal(max);
            System.out.println(current);

            total = total.subtract(current);

        }
    }


}
