package com.ihvn.validator.repositories;

import com.ihvn.validator.entities.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileUploadRepository extends JpaRepository  <FileUpload, UUID> {
}
