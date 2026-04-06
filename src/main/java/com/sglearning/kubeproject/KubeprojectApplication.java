package com.sglearning.kubeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KubeprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubeprojectApplication.class, args);
		System.out.println("Hello, Kubernetes!");
	}

}
