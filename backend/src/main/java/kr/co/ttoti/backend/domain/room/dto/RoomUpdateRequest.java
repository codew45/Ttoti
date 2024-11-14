package kr.co.ttoti.backend.domain.room.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoomUpdateRequest {

	@NotBlank
	private String name;
	@Min(4)
	@Max(8)
	private Integer participants;
	@Min(1)
	@Max(14)
	private Integer period;
	private LocalTime finishTime;
}
