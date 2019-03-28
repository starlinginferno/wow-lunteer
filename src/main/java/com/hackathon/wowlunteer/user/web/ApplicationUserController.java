package com.hackathon.wowlunteer.user.web;

import com.hackathon.wowlunteer.user.exceptions.EmailIsTakenException;
import com.hackathon.wowlunteer.user.exceptions.EmailNotValidException;
import com.hackathon.wowlunteer.user.exceptions.UserRoleNotFoundException;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import com.hackathon.wowlunteer.user.util.ApplicationUserDTO;
import com.hackathon.wowlunteer.user.util.FormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/api/user/register")
    @ResponseStatus(HttpStatus.OK)
    public RegisterResponse registerUser(@RequestBody @Valid ApplicationUserDTO applicationUserDTO)
            throws MethodArgumentNotValidException, EmailIsTakenException, UserRoleNotFoundException, EmailNotValidException {
        return applicationUserService.registerApplicationUser(applicationUserDTO);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return applicationUserService.verifyEmail(confirmationToken);
    }

    @PostMapping("/api/user/form")
    @ResponseStatus(HttpStatus.OK)
    public void completeForm(@RequestBody @Valid FormDTO formDTO, Principal principal) {
        ApplicationUser applicationUser = applicationUserService.getUserFromPrincipal(principal);
        applicationUserService.fillInForm(applicationUser, formDTO);
    }
}
