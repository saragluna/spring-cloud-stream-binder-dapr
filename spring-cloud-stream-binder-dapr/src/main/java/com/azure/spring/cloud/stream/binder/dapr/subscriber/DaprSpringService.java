// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.cloud.stream.binder.dapr.subscriber;

import com.google.protobuf.Empty;
import io.dapr.v1.AppCallbackGrpc;
import io.dapr.v1.DaprAppCallbackProtos;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Automatically configures and runs the gRPC server with @GrpcService implementations.
 */
@GrpcService
public class DaprSpringService extends AppCallbackGrpc.AppCallbackImplBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(DaprSpringService.class);
	private final List<DaprAppCallbackProtos.TopicSubscription> topicSubscriptionList = new ArrayList<>();

	private final List<Consumer<DaprAppCallbackProtos.TopicEventRequest>> consumers = new ArrayList<>();

	@Override
	public void listTopicSubscriptions(Empty request,
			StreamObserver<DaprAppCallbackProtos.ListTopicSubscriptionsResponse> responseObserver) {
		try {
			DaprAppCallbackProtos.ListTopicSubscriptionsResponse.Builder builder =
					DaprAppCallbackProtos.ListTopicSubscriptionsResponse.newBuilder();
			topicSubscriptionList.forEach(builder::addSubscriptions);
			DaprAppCallbackProtos.ListTopicSubscriptionsResponse response = builder.build();
			responseObserver.onNext(response);
		}
		catch (Throwable e) {
			responseObserver.onError(e);
		}
		finally {
			responseObserver.onCompleted();
		}
	}

	@Override
	public void onTopicEvent(DaprAppCallbackProtos.TopicEventRequest request,
			StreamObserver<DaprAppCallbackProtos.TopicEventResponse> responseObserver) {
		try {
			// TODO
			for (Consumer consumer : this.consumers) {
				consumer.accept(request);
			}

			DaprAppCallbackProtos.TopicEventResponse response =
					DaprAppCallbackProtos.TopicEventResponse.newBuilder()
							.setStatus(DaprAppCallbackProtos.TopicEventResponse.TopicEventResponseStatus.SUCCESS)
							.build();
			responseObserver.onNext(response);
		}
		catch (Throwable e) {
			responseObserver.onError(e);
		}
		finally {
			responseObserver.onCompleted();
		}
	}

	public boolean registerConsumer(String pubsubName, String topic, Consumer<DaprAppCallbackProtos.TopicEventRequest> consumer) {
		this.topicSubscriptionList.add(DaprAppCallbackProtos.TopicSubscription
				.newBuilder()
				.setPubsubName(pubsubName)
				.setTopic(topic)
				.build());
		this.consumers.add(consumer);
		return true;
	}

}
