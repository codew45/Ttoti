package kr.co.ttoti.backend.domain.ttoti.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.ttoti.entity.Temperature;
import kr.co.ttoti.backend.domain.ttoti.entity.TemperatureChangeReason;
import kr.co.ttoti.backend.domain.ttoti.repository.TemperatureRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemperatureInsertServiceImpl implements TemperatureInsertService {

	private static final Float BASE_TEMPERATURE = 36.5F;
	private static final Float MAX_TEMPERATURE = 100F;

	private final TemperatureRepository temperatureRepository;

	@Override
	@Transactional
	public Temperature calculateTemperatureIncrease(Integer ttotiId, Float prevTemperature,
		TemperatureChangeReason temperatureChangeReason, int roomPeriod) {

		float totalWeight = TemperatureChangeReason.calculateTotalWeight();
		Float increase = (MAX_TEMPERATURE - BASE_TEMPERATURE) *
			(temperatureChangeReason.getWeight() / totalWeight) / roomPeriod;

		increase = Math.round(increase * 10) / 10.0f;

		Float currentTemperature = Math.min(prevTemperature + increase, MAX_TEMPERATURE);

		return temperatureRepository.save(
			Temperature.builder()
				.ttotiId(ttotiId)
				.temperatureDifference(increase)
				.temperatureChangeReason(temperatureChangeReason)
				.currentTemperature(currentTemperature)
				.build()
		);
	}
}
