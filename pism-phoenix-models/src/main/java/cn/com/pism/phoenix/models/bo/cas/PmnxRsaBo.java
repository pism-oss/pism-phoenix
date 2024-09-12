package cn.com.pism.phoenix.models.bo.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author perccyking
 * @since 24-09-12 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmnxRsaBo {
    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

}
