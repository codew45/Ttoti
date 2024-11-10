package kr.co.ttoti.backend.domain.notification.service;

import java.util.concurrent.ExecutionException;

public interface NotificationSendService {

	void sendNotification(Integer memberId, String title, String content) throws ExecutionException, InterruptedException;
}
