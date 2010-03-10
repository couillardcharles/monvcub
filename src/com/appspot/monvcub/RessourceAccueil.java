package com.appspot.monvcub;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RessourceAccueil extends ServerResource {
	@Get
	public Representation represente() {
		TemplateRepresentation templateRepresentation = new TemplateRepresentation("accueil.xml", MediaType.TEXT_HTML);
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
