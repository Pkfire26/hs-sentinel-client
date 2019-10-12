package com.hico.models;

import java.util.HashSet;

import com.hico.models.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class StudentInfo {

    private UserInfo    user;
    private School      school;
    private HashSet<ClubAssociation> clubAssociations = new HashSet<ClubAssociation>();

    public StudentInfo() {}

    public StudentInfo(UserInfo user, School school, HashSet<ClubAssociation> clubs) {
        this.user = user;
        this.school = school;
        this.clubAssociations = clubAssociations;
        System.out.println("no of clubs = " + this.clubAssociations.size());
    }
}
