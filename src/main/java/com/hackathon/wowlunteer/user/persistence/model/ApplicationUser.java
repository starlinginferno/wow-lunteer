package com.hackathon.wowlunteer.user.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.hackathon.wowlunteer.event.persistence.model.Event;

import com.hackathon.wowlunteer.user.util.UserType;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public abstract class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    private String email;

    @Transient
    @Enumerated(EnumType.STRING)
    private UserType type;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private List<ApplicationUserRole> roles = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "applicationUser", targetEntity = ConfirmationToken.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ConfirmationToken confirmationToken;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @JsonManagedReference
    private List<Event> events = new ArrayList<>();

    public void setRoles(List<ApplicationUserRole> roles) {
        this.roles = roles;
    }
}
