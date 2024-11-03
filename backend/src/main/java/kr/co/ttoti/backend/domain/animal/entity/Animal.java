package kr.co.ttoti.backend.domain.animal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.ttoti.backend.domain.animal.dto.AnimalDto;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Integer animalId;

    @NotNull
    @Column(name = "animal_name")
    private String animalName;

    @NotNull
    @Column(name = "animal_image_url")
    private String animalImageUrl;

    @NotNull
    @Column(name = "animal_description")
    private String animalDescription;

    @NotNull
    @Column(name = "animal_is_available")
    private Boolean animalIsAvailable;

    public AnimalDto toDto(){
        return AnimalDto.builder()
            .animalName(this.animalName)
            .animalImageUrl(this.getAnimalImageUrl())
            .animalDescription(this.getAnimalDescription())
            .build();
    }
}
