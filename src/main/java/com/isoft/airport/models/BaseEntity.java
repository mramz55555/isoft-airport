package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedBy
    @Column(updatable = false, name = "created_by")
    protected String createdBy;
    @LastModifiedBy
    @Column(insertable = false, name = "updated_by")
    protected String updatedBy;
    @CreatedDate
    @Column(updatable = false, name = "created_at")
    protected LocalDate createdAt;
    @LastModifiedDate
    @Column(insertable = false, name = "updated_at")
    protected LocalDate updatedAt;

    public String getLocalDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("MMM dd,uuuu"));
    }
}
