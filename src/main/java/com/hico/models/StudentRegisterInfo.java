
package com.hico.models;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*
@Getter
@Setter
*/
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class StudentRegisterInfo {

    private String emailId;
    private String password;

    private String firstName;
    private String lastName;

    private String schoolId;
    private String regCode;

    public StudentRegisterInfo() {}
    public StudentRegisterInfo(String emailId, String pass,
            String fname, String lname, String schoolId,
            String rcode) {

        this.emailId = emailId;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.schoolId = schoolId;
        this.regCode = rcode;
    }
}
