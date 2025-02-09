// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.cloud.stream.binder.dapr.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Publisher configuration.
 */
@Configuration
public class DaprPublisherConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(DaprPublisherConfiguration.class);
	private int i = 0;

	@Bean
	public Supplier<Message<String>> supply() {
		return () -> MessageBuilder.withPayload("Hello world, " + i++).build();
	}

	@Bean
	public Supplier<Message<String>> supply2() {
		return () -> MessageBuilder.withPayload("Another hello world, " + i++).build();
	}

	@Bean
	public Consumer<Message<String>> consume() {
		return stringMessage -> LOGGER.info("Message received, {}", stringMessage );
	}

	@Bean
	public Consumer<Message<String>> consume2() {
		return stringMessage -> LOGGER.info("Another message received, {}", stringMessage);
	}

}
