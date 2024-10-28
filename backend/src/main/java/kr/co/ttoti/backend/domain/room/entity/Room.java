package kr.co.ttoti.backend.domain.room.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kr.co.ttoti.backend.global.base.BaseEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "room")
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    // FK
    @NotNull
    @Column(name = "room_host_member_id")
    private Integer roomHostMemberId;

    @NotNull
    @Column(name = "room_name")
    private Integer roomName;

    @NotNull
    @Min(value = 3)
    @Max(value = 8)
    @Column(name = "room_participants")
    private Integer roomParticipants;

    @NotNull
    @Column(name = "room_period")
    private Integer roomPeriod;

    @NotNull
    @Column(name = "room_code")
    private Integer roomCode;

    @NotNull
    @Column(name = "room_is_started")
    private Boolean roomIsStarted = false;

    @Column(name = "room_start_date")
    private LocalDate roomStartDate;

    @Column(name = "room_start_time")
    private LocalTime roomStartTime;

    @Column(name = "room_finish_date")
    private LocalDate roomFinishDate;

    @Column(name = "room_finish_time")
    private LocalTime roomFinishTime;

    @NotNull
    @Column(name = "room_is_finished")
    private Boolean roomIsFinished = false;
    
    @NotNull
    @Column(name = "room_is_deleted")
    private Boolean roomIsDeleted = false;

    @Column(name = "room_deleted_at")
    private LocalDateTime roomDeletedAt;
}