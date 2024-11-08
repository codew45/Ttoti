package kr.co.ttoti.backend.domain.member.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EndingDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ending_id")
	private Integer endingId;

	@NotNull
	@Column(name = "ttoti_id")
	private Integer ttotiId;

	@NotNull
	@Column(name = "ending_correct_score")
	private Float endingCorrectScore;

	@NotNull
	@Column(name = "ending_chat_count")
	private Integer endingChatCount;

	@NotNull
	@Column(name = "ending_final_temperature")
	private Float endingFinalTemperature;
}
