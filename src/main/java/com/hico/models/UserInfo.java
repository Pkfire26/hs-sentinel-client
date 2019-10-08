package com.hico.models;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class UserInfo {

    public String id;
    private String emailId;
    private String firstName;
    private String lastName;
    private String cellPhoneNo;

    // user is enabled / disabled.
    private boolean enabled;

    // user registration is complete.
    private boolean registered;

    // roles associated with the user.
    private HashSet<Role>   roles = new HashSet<Role>();

    private String schoolId;

    public UserInfo() {}

   public UserInfo(String emailId, String firstName, String lastName,
           String cellPhoneNo, String schoolId, String role) {
       this.emailId = emailId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.cellPhoneNo = cellPhoneNo;
       this.schoolId = schoolId;
       this.roles.add(new Role(role));

   }

   public void setId(String id) {
       this.id = id;
   }

   public String getId() {
       return this.id;
   }


   public boolean isEnabled() {
       return enabled;
   }

   public boolean isRegistered() {
       return registered;
   }

   public String getFirstName() {
       return this.firstName;
   }

   public void setFirstName(String firstName) {
       this.firstName = firstName;
   }

   public String getLastName() {
       return this.lastName;
   }

   public String getFullName() {
       return this.firstName + " " + this.lastName;
   }

   public void setEmailId(String emailId) {
       this.emailId = emailId;
   }

   public String getEmailId() {
        return this.emailId;
   }

   public HashSet<Role> getRoles() {
       return this.roles;
   }

   public void setRoles(HashSet<Role> roles) {
       this.roles = roles;
   }

   public void setCellPhoneNo(String cellPhoneNo) {
       this.cellPhoneNo = cellPhoneNo;
   }

   public String getCellPhoneNo() {
       return this.cellPhoneNo;
   }

   public void setSchoolId(String schoolId) {
       this.schoolId = schoolId;
   }
   public String getSchoolId() {
       return this.schoolId;
   }
}

