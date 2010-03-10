package com.appspot.monvcub;

import java.io.IOException;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.htmlparser.util.ParserException;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.repackaged.com.google.common.collect.Maps;

public class RessourceStations extends ServerResource {

	@Get
	public Representation represente() throws ParserException, IOException {
		Map<String, Object> données = Maps.newHashMap();
		données.put("stations", new ServiceStations().getStations());
		TemplateRepresentation templateRepresentation = new TemplateRepresentation("stations.xml", données,
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

}
