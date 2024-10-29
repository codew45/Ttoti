package kr.co.ttoti.backend.domain.animal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimalDto {

	String animalName;
	String animalImageUrl;
	String animalDescription;
}
