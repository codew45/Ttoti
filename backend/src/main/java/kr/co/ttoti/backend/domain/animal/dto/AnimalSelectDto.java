package kr.co.ttoti.backend.domain.animal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimalSelectDto {

	String animalName;
	String animalImageUrl;
}
