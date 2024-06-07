package com.cash.atm.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApiResponse {
    private Date time = new Date();
    private String message;
    private String path;

    public ApiResponse(String message, String path){
        this.message=message;
        this.path=path.replace("uri=", "");
    }
}