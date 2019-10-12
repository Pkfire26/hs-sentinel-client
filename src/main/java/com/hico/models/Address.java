
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

    public static Address construct(Request req) throws Exception {

        String street  = req.get("street", false);
        String city  = req.get("city", false);
        String state = req.get("state", false);
        String zip = req.get("zip");
        String country = req.get("country", false);

        return new Address (street, city, state, zip, country);
    }

}
