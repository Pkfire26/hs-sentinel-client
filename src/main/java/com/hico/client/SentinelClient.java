package com.hico.client;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.converter.*;
import org.json.*;

import com.hico.models.*;
import com.hico.config.*;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;

@Service
public class SentinelClient {

    private RestTemplate restTemplate;

    private String emailId;
    private String password;
    private String token;
    private String userId;
    private HashSet<String> roles;

    private static String STUDENT = "STUDENT";
    private static String TEACHER = "TEACHER";
    private static String ADMIM = "ADMIN";

    private static String baseURI = "http://localhost:8080";
    private static String loginURI = baseURI + "/api/auth/login";
    private static String listSchools = baseURI + "/api/school/";
    private static String LOOKUP_SCHOOL_URI  = baseURI + "/api/auth/lookup/schools";
    private static String REGISTER_URI = baseURI + "/api/auth/register";
    private static String STUDENT_URI = baseURI + "/api/student";
    private static String USER_URI = baseURI + "/api/user";
    private static String SCHOOL_URI = baseURI + "/api/school";
    private static String CLUB_URI = baseURI + "/api/club";
    private static String listStudents = STUDENT_URI + "/";


    public SentinelClient() {
        //this.restTemplate = new RestTemplate();
        this.restTemplate = new RequestFactory().getRestTemplate();
    }

    public MultiValueMap<String, String> getRequestHeaders(boolean addToken) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        if (addToken) {
            headers.add("Authorization", "Bearer " + this.token);
        }

        return headers;
    }

    public  void login(String emailId, String pass) throws Exception {

        this.emailId = emailId;
        this.password = pass;
        this.token = "";

        Login login = new Login(this.emailId, this.password);
        ObjectMapper mapper = new ObjectMapper();
        MultiValueMap<String, String> headers = this.getRequestHeaders(false);
        HttpEntity<Login> request = new HttpEntity<Login>(login, headers);
        LoginStatus loginResp = restTemplate.postForObject(loginURI, request, LoginStatus.class);

        this.token = loginResp.getToken();
        this.userId = loginResp.getUserId();
        this.roles = loginResp.getRoles();

        System.out.println(mapper.writeValueAsString(loginResp));
    }

    public  List<SchoolInfo> listSchools(Request req) {

        MultiValueMap<String, String> headers = this.getRequestHeaders(true);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<List<SchoolInfo>> schoolList = restTemplate.exchange(listSchools,
                HttpMethod.GET, request, 
                new ParameterizedTypeReference<List<SchoolInfo>>() {});

        System.out.println(schoolList);
        if(schoolList != null && schoolList.hasBody()){
            List<SchoolInfo> schools = schoolList.getBody();
            return schools;
        }
        return null;
    }

    public void registerSchool(School school) throws Exception {

        MultiValueMap<String, String> headers = this.getRequestHeaders(true);
        HttpEntity<School> request = new HttpEntity<School>(school, headers);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(request));
        String status = restTemplate.postForObject(REGISTER_URI, request, String.class);

        System.out.println(status);

    }

    // list schools. 
    public void listSchool(Map map) throws Exception {

    }


    // unregister a school. 
    // // list schools. 
    public void unregisterSchool(Request req) throws Exception {

    }


    public void updateSchool(Request req) throws Exception {

    }

    public String lookupSchoolId(SchoolLookupInfo info) throws Exception {

        MultiValueMap<String, String> headers = this.getRequestHeaders(false);
        HttpEntity<SchoolLookupInfo> request = new HttpEntity<SchoolLookupInfo>(info, headers);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(request));

        ResponseEntity<List<SchoolLookupInfo>> schoolList = restTemplate.exchange(LOOKUP_SCHOOL_URI,
                HttpMethod.GET, request,
                new ParameterizedTypeReference<List<SchoolLookupInfo>>() {});

        System.out.println(mapper.writeValueAsString(schoolList));
        if(schoolList != null && schoolList.hasBody()){
            List<SchoolLookupInfo> schools = schoolList.getBody();

            if (schools.size() == 0) {
                throw new Exception("No school found");
            }
            if (schools.size() > 1) {
                throw new Exception("More than one school found");
            }
            SchoolLookupInfo school = schools.get(0);
                return school.getId();
        }
        throw new Exception("No school found");
    }

    // register a student with the system
    public String register(StudentRegisterInfo info) throws Exception {
        MultiValueMap<String, String> headers = this.getRequestHeaders(false);
        HttpEntity<StudentRegisterInfo> request = new HttpEntity<StudentRegisterInfo>(info, headers);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(request));
        String status = restTemplate.postForObject(REGISTER_URI, request, String.class);

        System.out.println(status);
        return status;
    }

    public Profile getLoginProfile() throws Exception {
        return getProfile(this.userId);
    }

    public Profile getProfile(String id) throws Exception {

        MultiValueMap<String, String> headers = this.getRequestHeaders(true);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        String uri = USER_URI + "/" + id;
        ResponseEntity<Profile> profile = restTemplate.exchange(uri, HttpMethod.GET, request,
                Profile.class);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(profile.getBody()));

        return profile.getBody();
    }

    public List<StudentInfo> listStudents(Request req) throws Exception {

        MultiValueMap<String, String> headers = this.getRequestHeaders(true);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<List<StudentInfo>> studentList = restTemplate.exchange(listStudents,
                HttpMethod.GET, request, 
                new ParameterizedTypeReference<List<StudentInfo>>() {});

        System.out.println(studentList);
        if(studentList != null && studentList.hasBody()){
            List<StudentInfo> students = studentList.getBody();
            return students;
        }
        return null;
    }

    public void updateProfile(Request req) throws Exception {

    }

    public void unregisterStudent(Request req) throws Exception {

    }

    public List<Club>  listClubs(Request req) throws Exception {

        return null;
    }


    public void registerClub(Request req) throws Exception {

    }

    public void registerWithClub(Request req) throws Exception {

    }

    public void unregisterWithClub(Request req) throws Exception {

    }

    public void updateClubMemberStatus(Request req) throws Exception {

    }

}
