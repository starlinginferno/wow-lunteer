package com.hackathon.wowlunteer.security.auth.ajax;

import com.hackathon.wowlunteer.security.model.UserContext;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {


    private BCryptPasswordEncoder encoder;
    private ApplicationUserService applicationUserService;

    @Autowired
    public AjaxAuthenticationProvider(BCryptPasswordEncoder encoder, ApplicationUserService applicationUserService) {
        this.encoder = encoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        ApplicationUser applicationUser = applicationUserService.findByEmail(email);

        if (!encoder.matches(password, applicationUser.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        } else if (!applicationUserService.findByEmail(email).isEnabled()) {
            throw new BadCredentialsException("Verify your email first!");
        }

        UserContext userContext = applicationUserService.createUserContext(email);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

