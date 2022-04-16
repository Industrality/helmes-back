package com.helmesback.domain;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String userName;
    private Boolean agreedToTerms;
    private List<Long> sectors = new ArrayList<>();

}
