package com.hackathon.wowlunteer.user.web;

import com.hackathon.wowlunteer.user.service.VolunteerService;
import com.hackathon.wowlunteer.user.util.SearchDTO;
import com.hackathon.wowlunteer.user.util.VolunteerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VolunteerController {

    private VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/api/user/search")
    public List<VolunteerDTO> findVolunteersBy(@RequestBody SearchDTO searchDTO) {
        return volunteerService.searchVolunteers(searchDTO);
    }
}
