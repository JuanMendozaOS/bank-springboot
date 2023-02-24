package com.juan.bank.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Juan Mendoza
 */
public class DuplicateRecordException extends RuntimeException {

  public DuplicateRecordException(String error) {
    super(error);
  }
}

/*
public class DuplicateRecordException extends WebApplicationException {
     public DuplicateRecordException(Response.Status status, String message) {
         super(Response.status(status)
             .entity(message).type(MediaType.TEXT_PLAIN).build());
     }
}
 */
