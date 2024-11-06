package kr.co.ttoti.backend.domain.ttoti.common;

import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TtotiServiceUtils {

	private final TtotiRepository ttotiRepository;

	public void finishTtotiChat(Room room) {
		ttotiRepository.findByRoom(room).forEach(ttoti -> {
				ttoti.updateTtotiChatIsFinished(true);
			}
		);
	}
}
