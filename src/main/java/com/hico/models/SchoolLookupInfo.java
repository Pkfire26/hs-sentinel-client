
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
public class SchoolLookupInfo {

    private String id;
    private String name;
    private String zipCode;

    public SchoolLookupInfo() {}
    public SchoolLookupInfo(String name, String zipCode) {
        this.name = name;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }
}
