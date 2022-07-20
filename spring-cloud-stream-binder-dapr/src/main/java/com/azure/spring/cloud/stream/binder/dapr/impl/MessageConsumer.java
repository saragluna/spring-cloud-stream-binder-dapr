package com.azure.spring.cloud.stream.binder.dapr.impl;

import io.dapr.v1.DaprAppCallbackProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

class MessageConsumer implements Consumer<DaprAppCallbackProtos.TopicEventRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaprMessageProducer.class);
    private final String topic;
    private final String pubsubName;

    private Consumer<DaprAppCallbackProtos.TopicEventRequest> integrationConsumer;
    public MessageConsumer(String pubsubName, String topic, Consumer<DaprAppCallbackProtos.TopicEventRequest> integrationConsumer) {
        this.pubsubName = pubsubName;
        this.topic = topic;
        this.integrationConsumer = integrationConsumer;
    }

    @Override
    public void accept(DaprAppCallbackProtos.TopicEventRequest request) {
        if (this.topic.equals(request.getTopic()) && this.pubsubName.equals(request.getPubsubName())) {
            LOGGER.info("=====Received message from [{}]:[{}]", pubsubName, topic);
            integrationConsumer.accept(request);
        } else {
            return;
        }
    }
}
