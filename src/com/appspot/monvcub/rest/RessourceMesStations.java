package com.appspot.monvcub.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.htmlparser.util.ParserException;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.appspot.monvcub.stations.ServiceStations;
import com.google.appengine.repackaged.com.google.common.base.Function;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.appengine.repackaged.com.google.common.collect.Maps;

public class RessourceMesStations extends ServerResource {

	@Override
	protected void doInit() {
		String stations = getQuery().getFirstValue("stations", "");
		stationsPréférées = Lists.transform(Arrays.asList(stations.split(",")), new Function<String, Integer>() {
			@Override
			public Integer apply(String valeur) {
				Integer résultat;
				try {
					résultat = Integer.parseInt(valeur);
				} catch (NumberFormatException e) {
					résultat = 0;
				}
				return résultat;
			}
		});
	}
	
	@Get
	public Representation represente() throws ParserException, IOException {
		Map<String, Object> données = Maps.newHashMap();
		données.put("stations", new ServiceStations().getStations(stationsPréférées));
		TemplateRepresentation templateRepresentation = new TemplateRepresentation("mes-stations.xml", données,
				MediaType.TEXT_HTML);
		configureEngine(templateRepresentation.getEngine());
		return templateRepresentation;
	}

	private void configureEngine(VelocityEngine engine) {
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		engine.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
	}

	List<Integer> stationsPréférées;
}
