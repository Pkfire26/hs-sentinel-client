
package com.hico.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Address {

    public String street;
    public String city;
    public String state;
    public String zipCode;
    public String country;

    public Address() {}
    public Address(String street, String city,
            String state, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public static Address contruct(Map map) throws Exception {


        String street  = (String) map.get("street");
        String city  = (String) map.get("city");
        String state = (String) map.get("state");
        String zip = (String) map.get("zip");
        String country = (String) map.get("country");

        if ((zip == null) || (zip.trim().length == 0)) {
            throw Exception("Missing Zip code");
        }

        zip = zip.trim();

        return new Address (street, city, state, zip, country);
    }

}
