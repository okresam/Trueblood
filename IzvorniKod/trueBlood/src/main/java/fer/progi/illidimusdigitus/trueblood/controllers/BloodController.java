package fer.progi.illidimusdigitus.trueblood.controllers;

import fer.progi.illidimusdigitus.trueblood.controllers.util.BloodDTO;
import fer.progi.illidimusdigitus.trueblood.model.Blood;
import fer.progi.illidimusdigitus.trueblood.model.User;
import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;
import fer.progi.illidimusdigitus.trueblood.service.BloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BloodController {

    @Autowired
    private BloodService bloodService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/bloodGroups",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Blood> getBloodGroups() {

        List<Blood> bloodGroups = bloodService.findAll();

        return bloodGroups;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/bloodGroups")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Blood> changeBounds(@RequestBody BloodDTO dto) {

        BloodType type = switch(dto.getName()) {
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
        
        if(dto.getLowerbound() < 0) return ResponseEntity.badRequest().build();
        
        bloodService.updateBounds(type, dto);
        Blood blood = bloodService.findByName(type).get();
        
        if(blood.getSupply() < blood.getLowerbound()) {
        	bloodService.sendNotifLower(blood);
        }
        if(blood.getSupply() > blood.getUpperbound()) {
        	bloodService.sendNotifUpper(blood);
        }
        return ResponseEntity.ok().build();
    }
}
