package kr.co.ttoti.backend.global.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FcmConfig {

	@Value("${secret-key-path}")
	private String fcmSecretKeyPath;

	@PostConstruct
	public void init() {
		Resource resource;

		fcmSecretKeyPath = fcmSecretKeyPath;
		File file = new File(fcmSecretKeyPath);
		System.out.println("getAbsoluteFile 	"+file.getAbsoluteFile());
		System.out.println("exists	 "+file.exists());
		System.out.println("canRead 	"+file.canRead());
		System.out.println("isFile 		"+file.isFile());
		System.out.println("getAbsolutePath 	"+file.getAbsolutePath());


		if (file.exists()) {
			// 절대 경로가 존재할 때 FileSystemResource 사용
			resource = new FileSystemResource(fcmSecretKeyPath);
			System.out.println("파일시스템쓰는중");
		} else {
			// 클래스패스 경로로 가져오기
			resource = new ClassPathResource(fcmSecretKeyPath);
			System.out.println("클래스패스쓰는중");
		}

		System.out.println("FCMSECRETKEYPATH "+fcmSecretKeyPath);
		System.out.println("RESOUCE "+resource);

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