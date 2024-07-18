package com.library.book.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private String message;
    private String details;
    private long timestamp;

}
