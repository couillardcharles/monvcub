package com.appspot.monvcub.rest;

import java.io.IOException;
import java.util.Map;

import org.htmlparser.util.ParserException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.appspot.monvcub.stations.ServiceStations;
import com.google.common.collect.Maps;

public class RessourceStations extends ServerResource {

	@Get
	public Representation represente() throws ParserException, IOException {
		Map<String, Object> données = Maps.newHashMap();
		données.put("stations", new ServiceStations().getStations());
		return FabriqueTemplate.créeTemplate("stations.xml", données);
	}
}
