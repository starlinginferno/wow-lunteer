package com.hackathon.wowlunteer.file.persitence.repository;

import com.hackathon.wowlunteer.file.persitence.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
