package ksp.group3.miraiSugoroku.exception;

public class MiraiSugorokuException extends RuntimeException {
    private static final long serialVersionUID = -161631335611992421L;
    public static final int NO_SUCH_USER = 10;
    public static final int WRONG_PASSWORD = 11;
    public static final int ADMIN_PASSWORD_WRONG = 12;
    public static final int USED_LOGINID = 13;
    public static final int USER_NOT_FOUND = 14;
    public static final int USER_ALREADY_EXIST = 15;
    public static final int INVALID_USER_UPDATE = 16;
    public static final int INVALID_USER_ROLE = 17;
    public static final int CREATE_SQUARE_NOT_PERMITTED = 18;
    

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
