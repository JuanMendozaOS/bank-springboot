package com.juan.bank.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Juan Mendoza
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Record") 
public class RecordNotFoundException extends RuntimeException{
  public RecordNotFoundException (String error){
    super(error);
  }
}
