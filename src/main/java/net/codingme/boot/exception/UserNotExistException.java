package net.codingme.boot.exception;

import lombok.Data;

/**
 * @author liujinhui
 */
@Data
public class UserNotExistException extends RuntimeException {

    private static final long serialVersionUID = -6112780192479692859L;
    private String username;

    public UserNotExistException(String username) {
        super("user not exist");
        this.username = username;
    }

    public UserNotExistException() {
        super("user not exist");
    }



}
