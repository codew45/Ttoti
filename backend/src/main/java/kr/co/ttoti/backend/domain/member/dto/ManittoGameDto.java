package kr.co.ttoti.backend.domain.member.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ManittoGameDto {
    private Integer gameId;
    private String gameName;
    private List<String> membersName;
    private LocalDate startDate;
    private LocalDate endDate;
}
