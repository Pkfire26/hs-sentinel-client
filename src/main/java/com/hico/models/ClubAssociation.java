package com.hico.models;


import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ClubAssociation {

    // Club roles - teachers
    public static final String CLUB_SUPERVISOR      = "CLUB-SUPERVISOR";
    public static final String CLUB_ADVISOR         = "CLUB-ADVISOR";

    // Club roles - students
    public static final String CLUB_ADMIN           = "CLUB-ADMIN";
    public static final String CLUB_MEMBER          = "CLUB-MEMBER";
    public static final String CLUB_SUBSCRIBER      = "CLUB-SUBSCRIBER";

    private String   id;

    private String   clubId;
    private String   userId;
    private HashSet<String>  clubRoles = new HashSet<String>();
    private Date     joinDate;

    public ClubAssociation() {}

    public ClubAssociation(String clubId, String userId) {
        this.clubId = clubId;
        this.userId = userId;
    }


    public void addRole(String role) {
        this.clubRoles.add(role);
    }

    public void removeRole(String role) {
        this.clubRoles.remove(role);
    }
}

