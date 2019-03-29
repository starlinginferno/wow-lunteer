package com.hackathon.wowlunteer.user.service;

import com.hackathon.wowlunteer.security.auth.jwt.JwtAuthenticationToken;
import com.hackathon.wowlunteer.security.model.UserContext;
import com.hackathon.wowlunteer.user.exceptions.EmailIsTakenException;
import com.hackathon.wowlunteer.user.exceptions.EmailNotValidException;
import com.hackathon.wowlunteer.user.exceptions.UserRoleNotFoundException;
import com.hackathon.wowlunteer.user.persistence.model.*;
import com.hackathon.wowlunteer.user.persistence.repository.ApplicationUserRepository;
import com.hackathon.wowlunteer.user.persistence.repository.ConfirmationTokenRepository;
import com.hackathon.wowlunteer.user.persistence.repository.VolunteerRepository;
import com.hackathon.wowlunteer.user.util.ApplicationUserDTO;
import com.hackathon.wowlunteer.user.util.FormDTO;
import com.hackathon.wowlunteer.user.util.Role;
import com.hackathon.wowlunteer.user.util.UserType;
import com.hackathon.wowlunteer.user.web.RegisterResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hackathon.wowlunteer.user.util.UserFactory.createUser;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder encoder;
    private RoleService roleService;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  PasswordEncoder encoder, RoleService roleService, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService) {
        this.applicationUserRepository = applicationUserRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    public UserContext createUserContext(String email) {
        ApplicationUser applicationUser = findByEmail(email);

        if (applicationUser.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleEnum().authority()))
                .collect(Collectors.toList());

        return UserContext.create(applicationUser.getEmail(), authorities);
    }

    public ApplicationUser findByEmail(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public ApplicationUser findById(Long id) throws UsernameNotFoundException {
        return applicationUserRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    public ApplicationUser findByPrincipal(Principal principal) throws UsernameNotFoundException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) principal;
        UserContext userContext = (UserContext) authenticationToken.getPrincipal();
        String email = userContext.getEmail();
        return findByEmail(email);
    }

    public ApplicationUser createNewUser(ApplicationUserDTO applicationUserDTO) throws IllegalArgumentException {
        ApplicationUser applicationUser;
        for (UserType t : UserType.values()) {
            if (UserType.valueOf(applicationUserDTO.getType().toUpperCase()).equals(t)) {
                applicationUser = createUser(t);
                return applicationUser;
            }
        }
        throw new IllegalArgumentException("No such users type!");
    }

    public List<ApplicationUserRole> createRoleForUser(ApplicationUserDTO applicationUserDTO) throws UserRoleNotFoundException {
        ApplicationUserRole volunteerRole = new ApplicationUserRole(1L, Role.VOLUNTEER);
        roleService.saveRole(volunteerRole);
        ApplicationUserRole companyRole = new ApplicationUserRole(2L, Role.COMPANY);
        roleService.saveRole(companyRole);
        List<ApplicationUserRole> userRoles = new ArrayList<>();
        for (Role r : Role.values()) {
            if (r.getName().equals(applicationUserDTO.getType().toUpperCase())) {
                userRoles.add(roleService.findByRole(r));
            }
        }
        return userRoles;
    }


    public RegisterResponse registerApplicationUser(ApplicationUserDTO applicationUserDTO)
            throws EmailIsTakenException, UserRoleNotFoundException, EmailNotValidException {

        if (!applicationUserDTO.getEmail().matches("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            throw new EmailNotValidException("Not a valid email");
        }
        if (!existsByEmail(applicationUserDTO.getEmail())) {

                ApplicationUser applicationUser = createNewUser(applicationUserDTO);

                applicationUser.setEmail(applicationUserDTO.getEmail());
                applicationUser.setPassword(encoder.encode(applicationUserDTO.getPassword()));
                applicationUser.setRoles(createRoleForUser(applicationUserDTO));

                ConfirmationToken confirmationToken = new ConfirmationToken(applicationUser);
                applicationUser.setConfirmationToken(confirmationToken);
                applicationUserRepository.save(applicationUser);
                confirmationTokenRepository.save(confirmationToken);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(applicationUserDTO.getEmail());
                mailMessage.setSubject("Complete Registration!");
                mailMessage.setFrom("mailappforhackathon@gmail.com");
                mailMessage.setText("To confirm your account, please click here : "
                        +"http://hackathon.eu-west-3.elasticbeanstalk.com/confirm-account?token="+confirmationToken.getConfirmationToken());

                emailSenderService.sendEmail(mailMessage);
                return new RegisterResponse(applicationUser.getId(),
                        applicationUser.getEmail(), "Please verify your email address");
        }
        throw new EmailIsTakenException();
    }

    public String verifyEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        RegisterResponse registerResponse = new RegisterResponse();
        if (token != null) {
            ApplicationUser user = applicationUserRepository.findByEmailIgnoreCase(token.getApplicationUser().getEmail());
            user.setEnabled(true);
            applicationUserRepository.save(user);
            registerResponse.setVerification("Email verified");
        } else {
            registerResponse.setVerification("Verification failed");
        }
        return registerResponse.getVerification();
    }

    private Boolean existsByEmail(String email) {
        return applicationUserRepository.existsByEmail(email);
    }

    public String getUsersRole(ApplicationUser applicationUser) {
        String role = "";
        for (int i = 0; i < applicationUser.getRoles().size(); i++) {
            role = applicationUser.getRoles().get(i).getRoleEnum().getName();
        }
        return role;
    }

    public void fillInForm(ApplicationUser applicationUser, FormDTO formDTO) {
        ModelMapper mapper = new ModelMapper();
        if (getUsersRole(applicationUser).toUpperCase().equals("COMPANY")) {
            Company company = (Company) findById(applicationUser.getId());
            mapper.map(formDTO, company);
            applicationUserRepository.save(company);
        } else if (getUsersRole(applicationUser).toUpperCase().equals("VOLUNTEER")) {
            Volunteer volunteer = (Volunteer) findById(applicationUser.getId());
            mapper.map(formDTO, volunteer);
            applicationUserRepository.save(volunteer);
        }
    }
}
