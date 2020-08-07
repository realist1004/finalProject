package com.overwatch.test.exception;

// 사용자 정의 예외 처리?
// 만약, 수량이 부족하면 이 예외를 던짐.
public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException() {
        super();    // 부모 생성자를 생성함.
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    };

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    };
}
