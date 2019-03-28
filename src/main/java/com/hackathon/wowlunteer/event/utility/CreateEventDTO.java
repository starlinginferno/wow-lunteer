package com.hackathon.wowlunteer.event.utility;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateEventDTO {
    @NotNull
    private Long typeId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private Date start;
    @NotNull
    private Date finish;
}
