package com.hico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.hico.client.SentinelClient;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import com.hico.models.*;



//@SpringBootApplication
public class SentinelApplication {

//    @Autowired
    private SentinelClient sentinel;

    /*
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(SentinelApplication.class);
   }
   */

   public static void main(String[] args) {
       /*
       System.out.println("Starting Sentinel...");
       SpringApplication application = new SpringApplication(SentinelApplication.class);
       application.run(args);
       */
      SentinelApplication app = new SentinelApplication();
      try {
          app.run(args);
      }
      catch (Exception  ex) {
          ex.printStackTrace();
      }
   }

   private HashMap parseParams(String[] args) throws Exception {

       HashMap map = new HashMap<String,String>();

       String subject = args[0];
       String sub = "";
       if (subject.equals("-a")) {
           sub = "admin";
       }
       else if (subject.equals("-s")) {
           sub = "student";
       }
       else if (subject.equals("-t")) {
           sub = "teacher";
       }
       else  {
           throw new Exception("Invalid cmd");
       }

       map.put("kind", sub);
       for (int i = 1; i < args.length; i++ ) {
           String arg = args[i];
           String[] vals = arg.split("=");
           if (vals.length != 2) {
               throw new Exception ("Illegal cmd");
           }
           String key = vals[0];
           String val = vals[1];
           System.out.println(key + " : "  + val);
           map.put(key, val);
       }
       return map;
   }

   public void doStudent(HashMap map)  throws Exception {
       System.out.println("Student");

       String op = ((String)map.get("op")).trim();
       String userId = ((String) map.get("user")).trim();
       String pass = ((String) map.get("pass")).trim();
        if ((userId.length() == 0) || (pass.length() == 0)) {
            throw new Exception ("Missing Creds");
        }
        sentinel = new SentinelClient();
        if (op.equals("register")) {
            SchoolLookupInfo sInfo = getSchoolLookupInfo(map);
            String schoolId = sentinel.lookupSchoolId(sInfo);
            System.out.println("FOO:" + schoolId);
            map.put("schoolId", schoolId);
            StudentRegisterInfo info = getStudentRegisterInfo(map);
            sentinel.register(info);
        } else {
            sentinel.login(userId, pass);
            String type = ((String)map.get("type")).trim();
            if (op.equals("get")) {
                if (type.equals("profile")) {
                    sentinel.getLoginProfile();
                } else if (type.equals("clubs")) {
                    sentinel.listMyClubs(map);
                } else if (type.equals("all-clubs")) {
                    sentinel.listAllClubs(map);
                } else if (type.equals("club-members")) {
                    sentinel.listClubMemberStatus(map);
                }
            } else if (op.equals("update")) {
                if (type.equals("profile")) {
                    sentinel.upddateProfile(map);
                } else if (type.equals("register-club")) {
                    sentinet.registerWithClub(map);
                } else if (type.equals("unregister-club")) {
                    sentinet.registerWithClub(map);
                } else if (type.equals("club-status")) {
                    sentinel.updateClubMemberStatus(map);
                }
            }
        }
   }

   public void doTeacher(HashMap map)  throws Exception {
       System.out.println("Teacher");

       String op = ((String)map.get("op")).trim();
       String userId = ((String) map.get("user")).trim();
       String pass = ((String) map.get("pass")).trim();
       if ((userId.length() == 0) || (pass.length() == 0)) {
           throw new Exception ("Missing Creds");
       }

       sentinel = new SentinelClient();
       sentinel.login(userId, pass);
       if (op.equals("profile")) {
           sentinel.getLoginProfile();
       } else if (op.equals("lookup")) {
           String type = ((String)map.get("type"));
           if (type == null) {
               throw new Exception("Missing type param");
           }
           type = type.trim();
           /*
           if (type.equals("student")) {
               LookupInfo = getStudentLookupInfo(map);
               List<StudentInfo> infoList = sentinel.
           } else if (type.equals("clubs")) {
               LookupInfo = getClubLookupInfo(map);
               List<ClubInfo> clubs = sentinel.listClubs(info);
           }
           */
       }

   }

   public void doAdmin(HashMap map)  throws Exception {
       System.out.println("Admin");
       String userId = ((String) map.get("user")).trim();
       String pass = ((String) map.get("pass")).trim();
       if ((userId.length() == 0) || (pass.length() == 0)) {
           throw new Exception ("Missing Creds");
       }

       String op = ((String)map.get("op")).trim();

       sentinel = new SentinelClient();
       sentinel.login(userId, pass);

       if (op.equals("register")) {
           School school = School.construct(map);
           sentinel.registerSchool(school);
       } else if (op.inregister("get")) {
           sentinel.listSchools(map);
       } else if (op.equals("uodate")) {
           sentinel.updateSchool(map);
       } else if (op.equals("unregister")) {
           sentinel.unregisterSchool(map);
       }
   }

   public void doSchoolAdmin(HashMap map)  throws Exception {
       System.out.println("School Admin");
       String userId = ((String) map.get("user")).trim();
       String pass = ((String) map.get("pass")).trim();
       if ((userId.length() == 0) || (pass.length() == 0)) {
           throw new Exception ("Missing Creds");
       }
       String op = ((String)map.get("op")).trim();
       sentinel = new SentinelClient();
       if (op.equals("register")) {
           String type = ((String)map.get("type")).trim();
           if (type.equals("club")) {
               sentinel.registerClub(map);
           }


       }
   }

   public void run(String[] args) throws Exception {

       HashMap map = parseParams(args);

       String subject = (String)map.get("kind");
       if (subject.equals("admin")) {
           doAdmin(map);
       } else if (subject.equals("student")) {
           doStudent(map);
       } else if (subject.equals("teacher")) {
           doTeacher(map);
       } else if (subject.equals("school-admin")) {
           doSchoolAdmin(map);
       }
   }

   private SchoolLookupInfo getSchoolLookupInfo(HashMap map) throws Exception {
       String school = ((String) map.get("school")).trim();
       String zip = ((String) map.get("zip")).trim();

       SchoolLookupInfo info = new SchoolLookupInfo(school, zip);

       if ((school.length() == 0) || (zip.length() == 0)) {
           throw new Exception("Invalid params");
       }
       return info;

   }

   private StudentRegisterInfo getStudentRegisterInfo(HashMap map) throws Exception {

       String emailId = (String) map.get("user");
       String pass = (String) map.get("pass");
       String fname = (String) map.get("firstname");
       String lname = (String) map.get("lastname");
       String schoolId = (String) map.get("schoolId");
       String rcode = (String) map.get("regcode");

       if ((emailId == null) || (pass == null) || (fname == null) || (lname == null) ||
               (schoolId == null) || (rcode == null)) {
           throw new Exception("Missing Params");
        }

       if ((emailId.trim().length() == 0) || (pass.trim().length() == 0) ||
               (fname.trim().length() == 0) || (lname.trim().length() == 0) || 
               (schoolId.trim().length() == 0) ||
               (rcode.length() == 0)) {
           throw new Exception("Invalid params");
        }

       StudentRegisterInfo info = new StudentRegisterInfo(
               emailId, pass, fname, lname, schoolId, rcode);

       return  info;
   }
}


