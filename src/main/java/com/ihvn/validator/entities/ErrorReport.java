package com.ihvn.validator.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.ihvn.validator.models.EncounterErrors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "error_report")
public class ErrorReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "patient_id")
    private UUID patientId;

    @Type(type= "jsonb")
    @Column(name = "error_message", columnDefinition = "jsonb")
    private List<EncounterErrors> errorMessage;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }
}
