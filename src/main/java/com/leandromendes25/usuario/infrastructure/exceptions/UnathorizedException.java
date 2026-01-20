package com.leandromendes25.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnathorizedException extends AuthenticationException {
    public UnathorizedException(String message) {
        super(message);
    }
}
