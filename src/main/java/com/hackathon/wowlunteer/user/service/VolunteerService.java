package com.hackathon.wowlunteer.user.service;

import com.hackathon.wowlunteer.user.persistence.model.Volunteer;
import com.hackathon.wowlunteer.user.persistence.repository.VolunteerRepository;
import com.hackathon.wowlunteer.user.util.SearchDTO;
import com.hackathon.wowlunteer.user.util.VolunteerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    private VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<VolunteerDTO> mapVolunteers(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(v -> new VolunteerDTO(v.getEmail(), v.getFirstName(), v.getLastName(),
                        v.getMobileNumber(), v.getProfession(), v.getAge(), v.getIsLooking())).collect(Collectors.toList());
    }

    public List<VolunteerDTO> searchVolunteers(SearchDTO searchDTO) {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        if (searchDTO.getIsLooking()) {
            volunteers = volunteerRepository.findAllByIsLooking(true);
        }
                volunteers = volunteers.stream()
                .filter(p -> p.getProfession().contains(searchDTO.getProfession())).collect(Collectors.toList());
        return mapVolunteers(volunteers);
    }
}
