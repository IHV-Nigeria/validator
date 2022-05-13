package com.ihvn.validator.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="fileupload")
@Data
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, name = "facility_datim_code")
    private String facilityDatimCode;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_timestamp")
    private LocalDateTime fileTimestamp;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name="consumer_date")
    private LocalDateTime consumerDate;

    @Column(name="validator_date")
    private LocalDateTime validatorDate;

    @Column(name="deduplication_date")
    private LocalDateTime deduplicationDate;

    @Column(name="validation_report_json")
    private LocalDateTime validationReportJson;

    @Column(name="etl_date")
    private LocalDateTime etlDate;

    @Column(name="status")
    private String status;

    @Column(name = "patient_uuid", columnDefinition = "uuid")
    private UUID patientUuid;

}
