package kr.co.ttoti.backend.domain.room.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.global.base.BaseEntity;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "room_member")
public class RoomMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Integer roomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @NotNull
    @Column(name = "room_member_is_ready")
    private Boolean roomMemberIsReady = Boolean.FALSE;

    @Column(name = "room_member_deleted_at")
    private LocalDate roomMemberDeletedAt;

    @NotNull
    @Column(name = "room_member_is_deleted")
    private Boolean roomMemberIsDeleted = Boolean.FALSE;

    public RoomMember(Room room, Member member) {
        this.room = room;
        this.member = member;
        this.roomMemberIsReady = Boolean.FALSE;
        this.roomMemberIsDeleted = Boolean.FALSE;
    }

	public void updateAnimal(Animal animal){
		this.animal = animal;
	}

	public void updateRoomMemberIsReady(Boolean roomMemberIsReady){
		this.roomMemberIsReady = roomMemberIsReady;
	}
}