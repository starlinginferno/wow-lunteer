package com.hackathon.wowlunteer.file.persitence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hackathon.wowlunteer.user.persistence.model.ApplicationUser;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files")
public class DBFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    public DBFile() {
    }

    public DBFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
