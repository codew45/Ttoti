package kr.co.ttoti.backend.domain.ending.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kr.co.ttoti.backend.global.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "ending")
public class Ending extends BaseEntity {

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