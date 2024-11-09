package kr.co.ttoti.backend.domain.member.service;

import kr.co.ttoti.backend.domain.member.dto.EndingDto;
import kr.co.ttoti.backend.domain.member.dto.ManittoGamesRequest;
import kr.co.ttoti.backend.domain.member.dto.ManittoGameDto;
import kr.co.ttoti.backend.domain.member.dto.MemberDetailDto;

import java.util.List;

public interface MypageService {

    public void updateMemberName(Integer memberId, String newName);

    public List<ManittoGameDto> getManittoGameList(Integer memberId, ManittoGamesRequest manittoGamesRequest);

    List<MemberDetailDto> getManittoFriendList(Integer memberId);

    EndingDto getEnding(Integer memberId, Integer gameId);
}
