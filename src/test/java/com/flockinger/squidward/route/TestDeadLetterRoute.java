package com.flockinger.squidward.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Predicate;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.flockinger.squidward.processor.DeadLetterMessageCreator;

public class TestDeadLetterRoute extends CamelTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = DeadLetterRoute.DIRECT_DEADLETTER)
	protected ProducerTemplate template;

	@Before
	public void before() throws Exception {
		super.setUp();
		
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:application.properties");
		context.removeComponent("properties");
		context.addComponent("properties", pc);
		
		DeadLetterRoute route = new DeadLetterRoute();
		DeadLetterMessageCreator mockedMessageCreator = new DeadLetterMessageCreator();
		route.setMessageCreator(mockedMessageCreator);
		context().addRoutes(route);

		context().getRouteDefinition(DeadLetterRoute.ID).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(DeadLetterRoute.DEADLETTER_ENDPOINT).skipSendToOriginalEndpoint()
						.to(resultEndpoint);
			}
		});
	}

	@Test
	public void testRoute_withEmptyMessage_ShouldCreateDeadLetterAnyway() throws Exception {
		 resultEndpoint.expectedMessageCount(1);

		 template.sendBody((String)null);

		 resultEndpoint.assertIsSatisfied();
	}
	
	@Test
	public void testRoute_withNoHeader_ShouldCreateDeadLetterAnyway() throws Exception {
		 resultEndpoint.expectedMessageCount(1);
		 resultEndpoint.expectedMessagesMatches(new Predicate() {
				@Override
				public boolean matches(Exchange exchange) {	
					boolean isBodyOk = exchange.getIn().getBody(String.class).contains("message");
					boolean isHeaderOk = exchange.getIn().getHeader(Exchange.FILE_NAME).toString().contains("deadletter_");
					
					return isBodyOk && isHeaderOk;
				}
			});
		 template.sendBody("message");
		 resultEndpoint.assertIsSatisfied();
	}
	
	@Test
	public void testRoute_withFullExceptionMessage_ShouldCreateFullDeadLetter() throws Exception {
		 resultEndpoint.expectedMessageCount(1);
		 resultEndpoint.expectedMessagesMatches(new Predicate() {
				@Override
				public boolean matches(Exchange exchange) {	
					String body = exchange.getIn().getBody(String.class);
					return body.contains("breadcrumbId") && body.contains("java.lang.NullPointerException") 
							&& body.contains("Message History") && body.contains("myBody");
				}
			});
		 
		 Exchange ex = new DefaultExchange(context);
		 ex.setProperty(Exchange.EXCEPTION_CAUGHT, new NullPointerException());
		 Message message = new DefaultMessage();
		 message.setBody("myBody");
		 Map<String, Object> headers = new HashMap<>();
 
		 message.setHeaders(headers);
		 ex.setIn(message);
		 
		 template.send(ex);
		 
		 
		 resultEndpoint.assertIsSatisfied();
	}
	

	/*
	 * @Override protected RouteBuilder createRouteBuilder() throws Exception {
	 * return }
	 */

	@After
	public void after() throws Exception {
		super.tearDown();
	}
}
