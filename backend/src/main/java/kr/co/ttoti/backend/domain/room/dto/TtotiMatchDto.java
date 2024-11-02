package kr.co.ttoti.backend.domain.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TtotiMatchDto {

	private Integer myTtotiId;
	private Integer myTittoId;

	private String myManittoAnimalName;
	private String myManittoAnimalImageUrl;

	private String myAnimalName;
	private String myAnimalImageUrl;

	private String manitiMemberName;
	private String manitiProfileImageUrl;
}
