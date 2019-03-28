package com.hackathon.wowlunteer.security.endpoint;

import com.hackathon.wowlunteer.security.auth.jwt.extractor.TokenExtractor;
import com.hackathon.wowlunteer.security.auth.jwt.verifier.TokenVerifier;
import com.hackathon.wowlunteer.security.exceptions.InvalidJwtToken;
import com.hackathon.wowlunteer.security.model.UserContext;
import com.hackathon.wowlunteer.security.model.token.JwtToken;
import com.hackathon.wowlunteer.security.model.token.JwtTokenFactory;
import com.hackathon.wowlunteer.security.model.token.RawAccessJwtToken;
import com.hackathon.wowlunteer.security.model.token.RefreshToken;
import com.hackathon.wowlunteer.user.service.ApplicationUserService;
import com.hackathon.wowlunteer.user.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hackathon.wowlunteer.security.SecurityConstants.AUTHENTICATION_HEADER_NAME;
import static com.hackathon.wowlunteer.security.SecurityConstants.TOKEN_SIGNING_KEY;

@RestController
public class RefreshTokenEndpoint {

    private JwtTokenFactory tokenFactory;
    private ApplicationUserService applicationUserService;
    private TokenVerifier tokenVerifier;
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @Autowired
    public RefreshTokenEndpoint(JwtTokenFactory tokenFactory, ApplicationUserService applicationUserService, TokenVerifier tokenVerifier, TokenExtractor tokenExtractor) {
        this.tokenFactory = tokenFactory;
        this.applicationUserService = applicationUserService;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
    }

    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, UsernameNotFoundException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = null;
        try {
            refreshToken = RefreshToken.create(rawToken, TOKEN_SIGNING_KEY).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        UserContext userContext = applicationUserService.createUserContext(subject);
        return tokenFactory.createAccessJwtToken(userContext);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userNotFoundHandler(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}

