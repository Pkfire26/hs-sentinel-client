package com.hico.models;

import java.util.Map;
import java.util.HashMap;

import com.hico.models.*;


public class Request {

    public String Role;
    public String User;
    public String Pass;
    public String Op;
    public String Type;

    private HashMap map;


    public static final String OP_REGISTER = "register";
    public static final String OP_UNREGISTER = "unregister";
    public static final String OP_GET = "get";
    public static final String OP_UPDATE = "update";

    public static final String TYPE_SCHOOL  = "school";
    public static final String TYPE_STUDENT  = "student";
    public static final String TYPE_TEACHER  = "teacher";
    public static final String TYPE_CLUB  = "club";




    public Request(HashMap map) throws Exception {
        this.map = map;
        this.processRequest();
    }

    public boolean isRole(String role) {
        return this.Role.equals(role);
    }

    public void processRequest() throws Exception {

        this.Role = (String)map.get("kind");

        String user = ((String) map.get("user"));
        String pass = ((String) map.get("pass"));

        if ((user == null) || (user.trim().length() == 0) ||
                (pass == null) ||  (pass.length() == 0)) {
            throw new Exception ("Missing Creds");
        }
        this.User = user.trim();
        this.Pass = pass.trim();

        String op = ((String)map.get("op"));
        if (op == null) {
                throw new  Exception("Missing Op");
        }
        this.Op = op.trim();

        String type = ((String)map.get("type"));
        if (type == null) {
                throw new Exception("Missing type");
        }
        this.Type = type.trim();
    }


    public void augment(String key, String val) {
        this.map.put(key, val);
    }

    public String get(String key) throws Exception {
        return get(key, true);
    }

    // Get a request parameter
    public String get(String key, boolean strict) throws Exception {

        String val = ((String)map.get(key));
        if (strict && ((val == null) || (val.trim().length() == 0))) {
            throw new Exception("Missing value for " + key);
        }
        if (val != null) {
            val = val.trim();
        }
        return val;
    }

}

