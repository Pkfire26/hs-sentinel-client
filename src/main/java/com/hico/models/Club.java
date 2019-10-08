package com.hico.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Club {

    @Id
    private String id;
    private String name;
    private String description;
    private String schoolId;

    //private HashSet<String> owners;
    //private HashSet<String> admins;
    //private HashSet<String> subscribers;

    public Club() {}
    public Club(String name, String schoolId) {
        this.name = name;
        this.schoolId = schoolId;
    }

}

