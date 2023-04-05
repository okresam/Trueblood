package fer.progi.illidimusdigitus.trueblood.controllers;

import fer.progi.illidimusdigitus.trueblood.controllers.util.CreateUserDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.MessageDTO;
import fer.progi.illidimusdigitus.trueblood.controllers.util.UserInfoDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.Donation;
import fer.progi.illidimusdigitus.trueblood.model.Role;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.model.util.RoleName;
import fer.progi.illidimusdigitus.trueblood.service.BloodService;
import fer.progi.illidimusdigitus.trueblood.service.DonationService;
import fer.progi.illidimusdigitus.trueblood.service.RoleService;
import fer.progi.illidimusdigitus.trueblood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
//import javax.ws.rs.Consumes;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BloodService bloodService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private DonationService donationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final String activationURL;

    @Autowired
    public UserController(@Value("${activationURL}") String activationURL) {
        this.activationURL = activationURL;
    }

    //@CrossOrigin(origins = "*", allowCredentials = "true")
    //@GetMapping("")
    //public ResponseEntity<List<User>> listUsers() {
        //return ResponseEntity.ok().body(userService.listAll());
    //}

    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO dto, HttpServletRequest request) {

        Role userRole = roleService.findByName(RoleName.DONOR).get();

        BloodType userBT = switch(dto.getBloodTypeName()) {
          case "A+" -> BloodType.A_PLUS;
          case "A-" -> BloodType.A_MINUS;
          case "B+" -> BloodType.B_PLUS;
          case "B-" -> BloodType.B_MINUS;
          case "0+" -> BloodType.ZERO_PLUS;
          case "0-" -> BloodType.ZERO_MINUS;
          case "AB+" -> BloodType.AB_PLUS;
          case "AB-" -> BloodType.AB_MINUS;
          default -> BloodType.A_PLUS;
        };
        Blood userBloodType = bloodService.findByName(userBT).get();

        String nPass = alphaNumericString(8);
        System.out.println(nPass);

        User newUser = new User(
        		buildUsername(dto.getName(), dto.getSurname(), dto.getOib()),
                passwordEncoder.encode(nPass),
                dto.getName(),
                dto.getSurname(),
                dto.isGenderMale(),
                dto.getBirthplace(),
                dto.getOib(),
                dto.getAddress(),
                dto.getWorkplace(),
                dto.getEmail(),
                dto.getMobilePrivate(),
                dto.getMobileBusiness(),
                dto.getBirthdate(),
                userRole,
                userBloodType
        );

        userService.createUser(newUser);
        userService.sendMail(newUser,activationURL + "/user/add/confirm");
        return ResponseEntity.ok().build();
    }
    public String buildUsername(String name, String surname, String oib) {
    	String first = String.valueOf(name.charAt(0)).toUpperCase();
    	String second = String.valueOf(surname.charAt(0)).toUpperCase();
    	
    	switch(first) {
    		case "Č" -> first = "C";
    		case "Ć" -> first = "C";
    		case "Đ" -> first = "D";
    		case "Š" -> first = "S";
    		case "Ž" -> first = "Z";
    	}
    	System.out.println(first);
    	
    	switch(second) {
			case "Č" -> second = "C";
			case "Ć" -> second = "C";
			case "Đ" -> second = "D";
			case "Š" -> second = "S";
			case "Ž" -> second = "Z";
    	}
    	System.out.println(second);
    	
    	return first + second + oib.substring(6);
    	
    }
    /*@CrossOrigin(origins = "*")
    @PostMapping("/addAdmin")
    public ResponseEntity<User> createAdmin(@RequestBody CreateUserDTO dto, HttpServletRequest request) {

        Role userRole = roleService.findByName(RoleName.ADMIN).get();

        String nPass = alphaNumericString(8);
        System.out.println(nPass);

        User newUser = new User(
                String.valueOf(dto.getName().charAt(0)) + String.valueOf(dto.getSurname().charAt(0)) + dto.getOib().substring(6),
                passwordEncoder.encode(nPass),
                dto.getName(),
                dto.getSurname(),
                dto.getOib(),
                userRole,
                dto.getEmail()
                );

        userService.createUser(newUser);
        return ResponseEntity.ok().build();
    }*/
    
    @CrossOrigin(origins = "*")
    @PostMapping("/addDjelatnik")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> createDjelatnik(@RequestBody CreateUserDTO dto, HttpServletRequest request) {

        Role userRole = roleService.findByName(RoleName.DJELATNIK).get();

        BloodType userBT = switch(dto.getBloodTypeName()) {
            case "A+" -> BloodType.A_PLUS;
            case "A-" -> BloodType.A_MINUS;
            case "B+" -> BloodType.B_PLUS;
            case "B-" -> BloodType.B_MINUS;
            case "0+" -> BloodType.ZERO_PLUS;
            case "0-" -> BloodType.ZERO_MINUS;
            case "AB+" -> BloodType.AB_PLUS;
            case "AB-" -> BloodType.AB_MINUS;
            default -> BloodType.A_PLUS;
        };
        Blood userBloodType = bloodService.findByName(userBT).get();

        String nPass = alphaNumericString(8);
        System.out.println(nPass);

        User newUser = new User(
        		buildUsername(dto.getName(), dto.getSurname(), dto.getOib()),
                passwordEncoder.encode(nPass),
                dto.getName(),
                dto.getSurname(),
                dto.getOib(),
                userRole,
                userBloodType,
                dto.getEmail(), 
                dto.isGenderMale(),
                dto.getWorkplace(),
                dto.getMobilePrivate(),
                dto.getMobileBusiness(),
                dto.getBirthdate(),
                dto.getAddress(),
                dto.getBirthplace()
        );

        userService.createUser(newUser);

        userService.sendMail(newUser,activationURL + "/user/add/confirm");
        
        return ResponseEntity.ok().build();
    }
	
    @CrossOrigin(origins = "*")
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> loginAttempt(@RequestHeader String authorization) {
        authorization = authorization.substring(6);
        String decodedString = new String(Base64.getDecoder().decode(authorization));
        String[] userPass = decodedString.split(":");

        if (userService.findByUsername(userPass[0]).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User usr = userService.findByUsername(userPass[0]).get();
        System.out.println(userPass[1] + "\n" + usr.getPassword());
        if (!passwordEncoder.matches(userPass[1], usr.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        if (usr.getActivation() != null) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> userRole = new HashMap<>();
        userRole.put("role", usr.role.name.toString());
        return ResponseEntity.ok(userRole);
    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/add/confirm")
    public ResponseEntity<String> verifyUser(@RequestHeader String code, @RequestHeader String password) {
        //TREBA NAPRAVITI REGISTER PASSWORDA, A CODE POSLATI NA FRONTEND SITE
        if (userService.verify(code, password)) {
            return ResponseEntity.ok("verifySuccess=true");
        }
        return ResponseEntity.ok("verifySuccess=false");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getUserInfo")
    public ResponseEntity<CreateUserDTO> getUserInfo(@RequestHeader String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        String currUsername = context.getAuthentication().getName();
        List<?> authhshss = context.getAuthentication().getAuthorities().stream().collect(Collectors.toList());

        if (!currUsername.equals(username) && authhshss.get(0).equals("DONOR")) {
            return ResponseEntity.badRequest().build();
        }

        User usr = userService.findByUsername(username).get();

        CreateUserDTO userInfoDTO = new CreateUserDTO();

        userInfoDTO.setName(usr.getName());
        userInfoDTO.setSurname(usr.getSurname());
        userInfoDTO.setGenderMale(usr.isMale());
        userInfoDTO.setBirthplace(usr.getBirthplace());
        userInfoDTO.setOib(usr.getOib());
        userInfoDTO.setAddress(usr.getAddress());
        userInfoDTO.setWorkplace(usr.getWorkplace());
        userInfoDTO.setEmail(usr.getEmail());
        userInfoDTO.setMobilePrivate(usr.getMobilePrivate());
        userInfoDTO.setMobileBusiness(usr.getMobileBusiness());
        userInfoDTO.setBirthdate(usr.getBirthdate());
        userInfoDTO.setBloodTypeName(usr.getBloodType().getName().toString());
        userInfoDTO.setRejected(usr.isRejected());

        return ResponseEntity.ok(userInfoDTO);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/editUserInfo")
    public ResponseEntity<User> getEditUserInfo(@RequestBody UserInfoDTO newUserInfo, @RequestHeader String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        String currUsername = context.getAuthentication().getName();
        List<?> authhshss = context.getAuthentication().getAuthorities().stream().collect(Collectors.toList());

        if (!currUsername.equals(username) && authhshss.get(0).equals("DONOR")) {
            return ResponseEntity.badRequest().build();
        }
    	
        if (userService.updateUserInfo(username, newUserInfo)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getMessages",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> getMess(@RequestHeader String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        String currUsername = context.getAuthentication().getName();
        if (!currUsername.equals(username)) {
            return ResponseEntity.badRequest().build();
        }
   	
       User usr = userService.findByUsername(username).get();
       MessageDTO messages = new MessageDTO();
       
       if(usr.isRejected()) {
       	messages.setBelowLower(false);
       	messages.setMonths(false);
       	return ResponseEntity.ok(messages);
       }
       
       Blood blood =  bloodService.findByName(usr.getBloodType().getName()).get();
       if(blood.getSupply() < blood.getLowerbound()) messages.setBelowLower(true);
       else messages.setBelowLower(false);
       
       List<Donation> donations = donationService.findByDonor(usr);
       boolean gender = usr.isMale();
       
       donations.stream()
       		 .filter((e) -> e.getSuccess() == true)
       		 .collect(Collectors.toList());
       
       if(donations.isEmpty()) {
       		messages.setMonths(true);
       		
       }
       
       else {
    	   
    	   Set<Donation> don = new TreeSet<>(new Comparator<Donation>() {
               @Override
   			   public int compare(Donation o1, Donation o2) {
            	   if( o1.getDate().before(o2.getDate())) return 1;
            	   else return 0;
   			  }
           });
           don.addAll(donations);
           Donation last = don.iterator().next();
           
           
           if(gender == true) {
           	Date threeMonthsAgo = new Date();
        		GregorianCalendar cal = new GregorianCalendar();
        		cal.setTime(threeMonthsAgo);
        		cal.add(Calendar.DATE, -90);
        		cal.set(Calendar.HOUR_OF_DAY, 0);
        		cal.set(Calendar.MINUTE, 0);
        		cal.set(Calendar.SECOND, 0);
        		cal.set(Calendar.MILLISECOND, 0);
        		
        		if(cal.getTime().after(last.getDate())) {
        			messages.setMonths(true);
        			System.out.println("UNUTRA");
        		}
        		else messages.setMonths(false);
        	}
           else {
           	Date fourMonthsAgo = new Date();
        		GregorianCalendar cal = new GregorianCalendar();
        		cal.setTime(fourMonthsAgo);
        		cal.add(Calendar.DATE, -120);
        		cal.set(Calendar.HOUR_OF_DAY, 0);
        		cal.set(Calendar.MINUTE, 0);
        		cal.set(Calendar.SECOND, 0);
        		cal.set(Calendar.MILLISECOND, 0);
        		if(cal.getTime().before(last.getDate())) messages.setMonths(true);
        		else messages.setMonths(false);
           }
       }
       return ResponseEntity.ok(messages);
       
   }


}

