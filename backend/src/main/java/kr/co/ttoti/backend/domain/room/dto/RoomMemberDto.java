package kr.co.ttoti.backend.domain.room.dto;

import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMemberDto {

	String name;

	String profileImageUrl;

	Boolean isReady;

}