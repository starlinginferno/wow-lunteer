package com.hackathon.wowlunteer.event.utility;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class EventDTO {
    @NotNull
    private String type;
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

    public EventDTO(@NotNull String type, @NotNull String title, @NotNull String description, @NotNull String address, @NotNull Date start, @NotNull Date finish) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.address = address;
        this.start = start;
        this.finish = finish;
    }
}
