package com.appspot.monvcub.rest;

import java.io.IOException;

import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.appspot.monvcub.stations.ServiceStations;

public class RessourceStationsJSON extends ServerResource {

	@Get
	public Representation représente() throws IOException, JSONException {
		return new JsonRepresentation(new ServiceStations().getStationsJson());
	}
}
