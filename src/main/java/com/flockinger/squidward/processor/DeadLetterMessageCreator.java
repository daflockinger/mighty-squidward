package com.flockinger.squidward.processor;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.MessageHelper;
import org.springframework.stereotype.Component;
import com.flockinger.squidward.route.DeadLetterRoute;

@Component
public class DeadLetterMessageCreator implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String errorMessage = "";
		
		errorMessage += "\n\nHeaders:\n" + getHeaders(exchange);
		errorMessage += "\n\nStackTrace:\n" + exchange.getIn().getHeader(DeadLetterRoute.HEADER_STACKTRACE);
		errorMessage += "\n\nHistory:\n" + MessageHelper.dumpMessageHistoryStacktrace(exchange, null, false);
		errorMessage += "\n\nBody:\n" + exchange.getIn().getBody();
		
		exchange.getIn().setBody(errorMessage);
	}
	
	private String getHeaders(Exchange exchange){
		Map<String, Object> headers = exchange.getIn().getHeaders();
		
		return headers.keySet().stream()
		.filter(p -> !DeadLetterRoute.HEADER_STACKTRACE.equals(p))
		.map(key -> key + " = " + headers.get(key))
		.collect(Collectors.joining("\n"));
	}
}
