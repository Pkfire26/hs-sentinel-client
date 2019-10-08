
package com.hico.models;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Teacher {

    public String id;

    // User identity for this teacher
    private String userId;

    // school association
    private String schoolId;

    public Teacher() {}

    public Teacher(String userId, String schoolId) {
        this.userId = userId;
        this.schoolId = schoolId;
    }

    // id's of club associations.
    private HashSet<String> clubAssociations = new HashSet<String>();

    public void addClubAssociation(String assocId) {
        this.clubAssociations.add(assocId);
    }

    public void removeClubAssociation(String assocId) {
        this.clubAssociations.remove(assocId);
    }
}
