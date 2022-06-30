package com.surecloud.javatechnicalinterview.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "result")
@Data
@EqualsAndHashCode(of = "id")
public class ResultEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "date_taken", nullable = false)
    private LocalDate dateTaken;

}
