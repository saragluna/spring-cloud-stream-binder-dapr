// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.cloud.stream.binder.dapr.impl;

import com.azure.spring.cloud.stream.binder.dapr.subscriber.DaprSpringService;
import io.dapr.v1.DaprAppCallbackProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * The {@link DaprMessageProducer} is responsible for consuming the events.
 */
public class DaprMessageProducer extends MessageProducerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaprMessageProducer.class);
    private final DaprSpringService daprSpringService;
    public DaprMessageProducer(DaprSpringService daprSpringService,
                               String pubsubName,
                               String topic) {
        this.daprSpringService = daprSpringService;
        this.daprSpringService.registerConsumer(pubsubName, topic, new MessageConsumer(pubsubName, topic, this::onMessage));
    }

    private void onMessage(DaprAppCallbackProtos.TopicEventRequest request) {
        // TODO converter
        Message<String> message = MessageBuilder.withPayload(request.getData().toString()).build();
        sendMessage(message);
    }

}
