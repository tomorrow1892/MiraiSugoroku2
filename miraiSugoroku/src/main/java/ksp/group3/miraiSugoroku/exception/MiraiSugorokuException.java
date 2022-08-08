package ksp.group3.miraiSugoroku.exception;

public class MiraiSugorokuException extends RuntimeException{
    private static final long serialVersionUID = -161631335611992421L;
    public static final int ERROR = 99;

    int code;

    public MiraiSugorokuException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MiraiSugorokuException(int code, String message, Exception cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
