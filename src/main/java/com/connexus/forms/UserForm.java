package com.connexus.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {

    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;

}
