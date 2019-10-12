package com.hico.models;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class School {

    private String id;

    private String          name;
    private Address         address;
    private List<String>    phoneNos;
    private HashSet<String> schoolAdmins;

    // code for self registration to school.
    private String regCode;

    public School() {}

    public School(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void setPhoneNos(List<String> phoneNos) {
        this.phoneNos = phoneNos;
    }

    public void setSchoolAdmins(HashSet<String> admins) {
        this.schoolAdmins = admins;
    }

    public HashSet<String> getSchoolAdmins() {
        return this.schoolAdmins;
    }

    public static School construct(Request req) throws Exception {

        String name = req.get("name");
        Address address = Address.construct(req);
        String phonesStr = req.get("phone", false);

        String regCode = req.get("regcode", false);
        String adminStr = req.get("admins", false);

        School school = new School(name, address);

        if (adminStr != null) {
            String[] tAdmins =  adminStr.split(",");
            HashSet<String> admins = new HashSet<String> ();
            for (String tAdmin : tAdmins) {
                admins.add(tAdmin);
            }
            school.setSchoolAdmins(admins);
        }

        if (phonesStr != null) {
            String[] phones =  phonesStr.split(",");
            ArrayList<String> phList = new ArrayList<String> ();
            for (String phone : phones) {
                phList.add(phone);
            }
            school.setPhoneNos(phList);
        }
        return school;
    }
}
