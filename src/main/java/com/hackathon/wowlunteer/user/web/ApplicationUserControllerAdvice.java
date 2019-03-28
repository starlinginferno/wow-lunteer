package com.hackathon.wowlunteer.user.web;

import com.hackathon.wowlunteer.user.exceptions.EmailIsTakenException;
import com.hackathon.wowlunteer.user.exceptions.EmailNotValidException;
import com.hackathon.wowlunteer.user.exceptions.UserRoleNotFoundException;
import com.hackathon.wowlunteer.user.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = ApplicationUserController.class)
public class ApplicationUserControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EmailIsTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse usernameIsTakenHandler() {
        return new ErrorResponse("Email already taken, please choose another one.");
    }

    @ResponseBody
    @ExceptionHandler(UserRoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userRoleNotFoundHandler(UserRoleNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse userRoleNotFoundHandler(AccessDeniedException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmailNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse emailNotValidHandler(EmailNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
