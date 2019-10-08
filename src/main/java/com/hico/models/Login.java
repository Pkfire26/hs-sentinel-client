package com.hico.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/*
@Getter
@Setter
@ToString
*/
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Value
public class Login {

    private String emailId;
    private String password;

    public Login() {}
    public Login(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }
}


