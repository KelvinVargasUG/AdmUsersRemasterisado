package com.kjvargas.admusers.Configs.Exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private List<String> errors;
}