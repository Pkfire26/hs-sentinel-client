package com.hico.models;

import java.util.List;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class SchoolInfo {

    private String id;

    private String          name;
    private Address         address;
    private List<String>    phoneNos;
    private HashSet<String> schoolAdmins;

    // code for self registration to school.
    private String regCode;

    public SchoolInfo() {}

    public SchoolInfo(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
