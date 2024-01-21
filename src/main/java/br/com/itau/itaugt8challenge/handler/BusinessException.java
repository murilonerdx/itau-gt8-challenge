package br.com.itau.itaugt8challenge.handler;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }
}
