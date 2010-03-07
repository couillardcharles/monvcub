package com.appspot.monvcub;

import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RessourceAccueil extends ServerResource {
	@Get
	public StringRepresentation represente() {
		return new StringRepresentation("hello, world ");
	}
}
