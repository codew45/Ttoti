package kr.co.ttoti.backend.global.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FcmConfig {

	@Value("${fcm.secret-key-path}")
	private String fcmSecretKeyPath;

	@PostConstruct
	public void init() {
		System.out.println(fcmSecretKeyPath);
		ClassPathResource resource = new ClassPathResource(fcmSecretKeyPath);

		try (InputStream inputStream = resource.getInputStream()) {
			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(inputStream))
				.build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				System.out.println("Firebase app init");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}