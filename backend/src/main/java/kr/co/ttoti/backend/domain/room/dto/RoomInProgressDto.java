package kr.co.ttoti.backend.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomInProgressDto {

	private String roomHostMemberName;
	private String roomName;
	private TtotiMatchDto ttotiMatchInfo;
}
