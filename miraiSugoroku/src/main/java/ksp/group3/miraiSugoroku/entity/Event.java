package ksp.group3.miraiSugoroku.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long eventId;

    int nGroups;

    Date startDate;

    Date limitDate;

    String name;

    boolean isApproved;

    Long uid; //イベントのサブ管理者ID
}