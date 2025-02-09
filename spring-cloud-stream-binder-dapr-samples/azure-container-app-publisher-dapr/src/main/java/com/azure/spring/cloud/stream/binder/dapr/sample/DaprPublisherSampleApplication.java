// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.cloud.stream.binder.dapr.sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaprPublisherSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaprPublisherSampleApplication.class, args);
	}
}
