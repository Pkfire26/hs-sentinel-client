package com.hico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import com.hico.client.SentinelClient;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
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

       System.out.println(args);
       String subject = args[0];
       String sub = "";
       if (subject.equals("-o")) {
           sub = Role.ADMIN;
       }
       else if (subject.equals("-s")) {
           sub = Role.STUDENT;
       }
       else if (subject.equals("-t")) {
           sub = Role.TEACHER;
       }
       else if (subject.equals("-a")) {
           sub = Role.SCHOOL_ADMIN;
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

   public void doStudent(Request req)  throws Exception {
       System.out.println("Student");

        sentinel = new SentinelClient();
        if (req.Op.equals(Request.OP_REGISTER)) {
            if (req.Type.equals(Request.TYPE_STUDENT)) {
                SchoolLookupInfo sInfo = getSchoolLookupInfo(req);
                String schoolId = sentinel.lookupSchoolId(sInfo);
                System.out.println("FOO:" + schoolId);
                req.augment("schoolId", schoolId);
                StudentRegisterInfo info = getStudentRegisterInfo(req);
                sentinel.register(info);
            } else if (req.Type.equals(Request.TYPE_CLUB)) {
                sentinel.registerWithClub(req);
            }
        } else {
            sentinel.login(req.User, req.Pass);
            if (req.Op.equals(Request.OP_GET)) {
                if (req.Type.equals(Request.TYPE_STUDENT)) {
                    sentinel.getLoginProfile();
                } else if (req.Type.equals(Request.TYPE_CLUB)) {
                    sentinel.listClubs(req);
                }
            } else if (req.Op.equals(Request.OP_UPDATE)) {
                if (req.Type.equals(Request.TYPE_STUDENT)) {
                    sentinel.updateProfile(req);
                } else if (req.Type.equals(Request.TYPE_CLUB)) {
                    sentinel.updateClubMemberStatus(req);
                }
            } else if (req.Op.equals(Request.OP_UNREGISTER)) {
                if (req.Type.equals(Request.TYPE_STUDENT)) {
                    sentinel.unregisterStudent(req);
                } else if (req.Type.equals(Request.TYPE_CLUB)) {
                    sentinel.unregisterWithClub(req);
                }
            }
        }
   }

   public void doTeacher(Request req)  throws Exception {
       System.out.println("Teacher");

       sentinel = new SentinelClient();
       sentinel.login(req.User, req.Pass);
       if (req.Type.equals(Request.OP_GET)) {
           if (req.Type.equals(Request.TYPE_TEACHER)) {
               sentinel.getLoginProfile();
           }
           else if (req.Type.equals(Request.TYPE_STUDENT)) {
               List<StudentInfo> students = sentinel.listStudents(req);
               System.out.println("student count: " + students.size());
               ObjectMapper mapper = new ObjectMapper();
               System.out.println(mapper.writeValueAsString(students));
           } else if (req.Type.equals(Request.TYPE_CLUB)) {
               List<Club> clubs = sentinel.listClubs(req);
           }
       } else if (req.Op.equals(Request.OP_UPDATE)) {
           if (req.Type.equals(Request.TYPE_TEACHER)) {
           }
       } else if (req.Op.equals(Request.OP_UNREGISTER)) {

       }
   }


   public void doAdmin(Request req)  throws Exception {
       System.out.println("Admin");

       sentinel = new SentinelClient();
       sentinel.login(req.User, req.Pass);

       if (req.Op.equals(Request.OP_REGISTER)) {
           School school = School.construct(req);
           sentinel.registerSchool(school);
       } else if (req.Op.equals(Request.OP_GET)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               List<SchoolInfo> schools = sentinel.listSchools(req);
               ObjectMapper mapper = new ObjectMapper();
               System.out.println(mapper.writeValueAsString(schools));
           } else if (req.Type.equals(Request.TYPE_STUDENT)) {
               List<StudentInfo> students = sentinel.listStudents(req);
               System.out.println("student count: " + students.size());
               ObjectMapper mapper = new ObjectMapper();
               System.out.println(mapper.writerWithDefaultPrettyPrinter().
                       writeValueAsString(students));
           }
       } else if (req.Op.equals(Request.OP_UPDATE)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               sentinel.updateSchool(req);
           }
       } else if (req.Op.equals(Request.OP_UNREGISTER)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               sentinel.unregisterSchool(req);
           }
       }
   }

   public void doSchoolAdmin(Request req)  throws Exception {
       System.out.println("School Admin");

       sentinel = new SentinelClient();
       if (req.Op.equals(Request.OP_REGISTER)) {
           if (req.Type.equals(Request.TYPE_CLUB)) {
               sentinel.registerClub(req);
           }
       } else if (req.Op.equals(Request.OP_GET)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               List<SchoolInfo> schools = sentinel.listSchools(req);
               ObjectMapper mapper = new ObjectMapper();
               System.out.println(mapper.writeValueAsString(schools));
           } else if (req.Type.equals(Request.TYPE_STUDENT)) {
               List<StudentInfo> students = sentinel.listStudents(req);
               System.out.println("student count: " + students.size());
               ObjectMapper mapper = new ObjectMapper();
               System.out.println(mapper.writeValueAsString(students));

           }
       } else if (req.Op.equals(Request.OP_UPDATE)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               sentinel.updateSchool(req);
           }
       } else if (req.Op.equals(Request.OP_UNREGISTER)) {
           if (req.Type.equals(Request.TYPE_SCHOOL)) {
               sentinel.unregisterSchool(req);
           }
       } else {
       }
   }

   public void doNothing(Request req)  throws Exception {
        System.out.println("Invalid roles");
   }

   public void run(String[] args) throws Exception {

       HashMap map = parseParams(args);

       Request req = new Request(map);

       if (req.isRole(Role.ADMIN)) {
           doAdmin(req);
       } else if (req.isRole(Role.SCHOOL_ADMIN)) {
           doSchoolAdmin(req);
       } else if (req.isRole(Role.STUDENT)) {
           doStudent(req);
       } else if (req.isRole(Role.TEACHER)) {
           doTeacher(req);
       } else  {
           doNothing(req);
       }
   }

   private SchoolLookupInfo getSchoolLookupInfo(Request req) throws Exception {

       String school = req.get("school", true);
       String zip = req.get("zip", true);

       SchoolLookupInfo info = new SchoolLookupInfo(school, zip);
       return info;

   }

   private StudentRegisterInfo getStudentRegisterInfo(Request req) throws Exception {

       String emailId = req.User;
       String pass = req.Pass;
       String fname = req.get("firstname");
       String lname = req.get("lastname");
       String schoolId = req.get("schoolId");
       String rcode = req.get("regcode");

       StudentRegisterInfo info = new StudentRegisterInfo(
               emailId, pass, fname, lname, schoolId, rcode);

       return  info;
   }
}


