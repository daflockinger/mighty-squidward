package com.flockinger.squidward.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flockinger.squidward.processor.DeadLetterMessageCreator;

@Component
public class DeadLetterRoute extends RouteBuilder{

	public static final String DIRECT_DEADLETTER = "direct:deadLetter";
	public static final String HEADER_STACKTRACE = "squidStacktrace";
	public static final String DEADLETTER_ENDPOINT = "{{squidward.deadletter.endpoint}}";
	public static final String ID = "deadLetterRoute";
	
	@Autowired
	private DeadLetterMessageCreator messageCreator;
	
	@Override
	public void configure() throws Exception {
		from(DIRECT_DEADLETTER)
			.id(ID)
			.setHeader(HEADER_STACKTRACE, simple("${exception.stacktrace}"))
			.process(messageCreator)
			.log(LoggingLevel.ERROR, "Dead Letter: ${body}")
			.setHeader(Exchange.FILE_NAME,simple("deadletter_${date:now: dd-MM-yyyy_hh-mm-ss}.txt"))
		.to(DEADLETTER_ENDPOINT);
	}
	
	public static ErrorHandlerBuilder getErrorHandler(){
		DeadLetterChannelBuilder deadLetterHandler = new DeadLetterChannelBuilder(DeadLetterRoute.DIRECT_DEADLETTER);
		deadLetterHandler.redeliveryDelay(5000).allowRedeliveryWhileStopping(false).maximumRedeliveries(3);
		
		return deadLetterHandler;
	}
	
	public void setMessageCreator(DeadLetterMessageCreator messageCreator) {
		this.messageCreator = messageCreator;
	}
}
