package com.aamsis.springsecuritypractice.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    private String value;
    private Integer userId;
    private String username;
    private Date creationDate;
    private boolean isValid;
    private Date jwtExpiration;
}
