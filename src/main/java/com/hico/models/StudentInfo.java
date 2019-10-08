
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
public class StudentInfo {

    private String emailId;
    private String password;

    private String firstName;
    private String lastName;
    private String schoolName;
    private String schoolZip;
    private String regCode;

    public StudentInfo() {}
    public StudentInfo(String emailId, String pass,
            String fname, String lname, String school,
            String zip, String rcode) {

        this.emailId = emailId;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.schoolName = school;
        this.schoolZip = zip;
        this.regCode = rcode;
    }
}
