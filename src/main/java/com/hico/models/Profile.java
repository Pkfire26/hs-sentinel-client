package com.hico.models;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Profile {

    private UserInfo        user;
    private SchoolInfo      school;
    private HashSet<ClubAssociation> clubAssociations;

    public Profile() {}
    public Profile(UserInfo user, SchoolInfo school, HashSet<ClubAssociation> clubs) {

        this.user = user;
        this.school = school;
        this.clubAssociations = clubs;
    }
}
