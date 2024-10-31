package kr.co.ttoti.backend.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TtotiMatchDto {

	private String ttotiAnimalName;
	private String ttotiAnimalImageUrl;
	private String manitiMemberName;
	private String manitiProfileImageUrl;
}
