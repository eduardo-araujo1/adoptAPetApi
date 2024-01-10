package com.eduardo.AdoptAPetAPI.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorMessage {

    private HttpStatus status;
    private String message;
}
