package br.api.coursewebflux.exceptions;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
@Builder
@Data
public class StandardError implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private LocalDateTime timesTamp;
        private String message;
        private String path;
        private Integer status;
        private String error;


}
