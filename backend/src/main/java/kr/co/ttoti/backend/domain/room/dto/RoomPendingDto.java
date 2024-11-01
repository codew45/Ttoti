package kr.co.ttoti.backend.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomPendingDto {

	String hostName;

	String roomName;

	RoomMemberListPendingDto roomMemberInfo;

	Boolean isReady;

	String profileImageUrl;
}
