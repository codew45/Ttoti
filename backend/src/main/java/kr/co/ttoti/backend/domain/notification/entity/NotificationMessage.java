package kr.co.ttoti.backend.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationMessage {

	GAME_START("마니또 게임이 시작됐어요!", "마니또 게임이 시작됐어요! 나만의 마니또, 마니띠와 즐거운 시간을 보내보세요!"),
	TODAY_QUIZ_OPENED("오늘의 퀴즈가 공개됐어요!", "오늘의 퀴즈와 어제의 퀴즈 정답이 공개됐어요!"),
	MANITTO_GUESS_OPENED("내 마니또는 누구일까?","내 마니또는 누구일까? 추측하러 가볼까요?"),
	MANITTO_CHAT("마니또에게 메시지가 왔어요!", "답장하러 가볼까요?"),
	MANITI_CHAT("마니띠에게 메시지가 왔어요!", "답장하러 가볼까요?"),
	FINAL_MANITTO_GUESS_OPENED("내 마니또는 누구일까?", "내일은 마니또의 정체가 밝혀지는 날이에요! 내 마니또는 누구일지 추측하러 가볼까요?"),
	GAME_END("마니또의 정체가 공개됐어요!", "마니또의 정체가 공개됐어요! 내 마니또는 누구였을까? 마니또는 가면을 벗고 정체를 공개해주세요!!!!!")


	;

	private final String title;
	private final String content;
}
