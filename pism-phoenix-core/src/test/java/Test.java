import cn.com.pism.phoenix.utils.Jackson;

/**
 * @author perccyking
 * @since 24-09-17 23:22
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Jackson.toJsonString("123"));
        String s = Jackson.parseObject("123", String.class);
        System.out.println(s);
    }
}
