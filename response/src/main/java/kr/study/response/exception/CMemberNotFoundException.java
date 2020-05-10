package kr.study.response.exception;

public class CMemberNotFoundException extends RuntimeException {

    public CMemberNotFoundException() {
        super();
    }

    public CMemberNotFoundException(String message) {
        super(message);
    }

    public CMemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
