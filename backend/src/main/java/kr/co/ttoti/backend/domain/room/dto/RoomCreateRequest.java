package kr.co.ttoti.backend.domain.room.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoomCreateRequest {

	@NotBlank
	private String name;
	@Min(3)
	@Max(8)
	private Integer participants;
	@Min(3)
	@Max(14)
	private Integer period;
	// scheduling 시간과 겹치지 않도록, 30분 단위이도록
	private LocalTime finishTime;
}
