package com.example.musicarchivebackend;

import com.example.musicarchivebackend.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MusicArchiveBackendApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext =  SpringApplication.run(MusicArchiveBackendApplication.class, args);
		StorageService storageService = applicationContext.getBean(StorageService.class);
		System.out.println(storageService.getSongFileNames());
	}

}
