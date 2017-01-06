package com.flockinger.squidward.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Message;

import com.flockinger.squidward.model.RoutePath;



/**
 * Checks if Message contains a non-null body
 * and if a filled RoutePath with a start-entry
 * is in the header
 */
public class IsProcessStartableCheck implements Predicate{

	@Override
	public boolean matches(Exchange exchange) {
		boolean isStartable = false;
		Message message = exchange.getIn();
		
		if(message != null && message.getBody() != null && isConfigValid(message)){
			isStartable = true;
		}
		
		return isStartable;
	}
	
	private boolean isConfigValid(Message message){
		RoutePath path = message.getHeader(RoutePath.HEADER_ROUTE_PATH,RoutePath.class);
		
		if(path != null){
			
		}
		
		return false;
	}
}
