package com.hico.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.io.Serializable;
import java.util.HashSet;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class LoginStatus  implements Serializable {

    private int     status;
    private String  message;
    private String  userId;
    private String  token;
    private HashSet<String>  roles;

    public LoginStatus(){}
    public LoginStatus(int status, String message, String userId, String token) {
        this.status = status;
        this.message = message;
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
    public String getUserId() {
        return this.userId;
    }

    public HashSet<String> getRoles() {
        return this.roles;
    }
}


