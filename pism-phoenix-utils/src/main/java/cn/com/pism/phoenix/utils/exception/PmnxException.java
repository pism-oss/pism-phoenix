package cn.com.pism.phoenix.utils.exception;

/**
 * @author perccyking
 * @since 24-06-22 18:16
 */
public class PmnxException extends RuntimeException{
    public PmnxException() {
    }

    public PmnxException(String message) {
        super(message);
    }

    public PmnxException(String message, Throwable cause) {
        super(message, cause);
    }

    public PmnxException(Throwable cause) {
        super(cause);
    }

    public PmnxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
