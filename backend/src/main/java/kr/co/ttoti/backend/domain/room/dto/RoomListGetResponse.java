package kr.co.ttoti.backend.domain.room.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomListGetResponse {

	List<RoomSummaryDto> rooms;
}
