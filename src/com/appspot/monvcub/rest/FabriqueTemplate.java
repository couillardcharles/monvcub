package com.appspot.monvcub.rest;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;

public class FabriqueTemplate {
	public static TemplateRepresentation créeTemplate(String template) {
		TemplateRepresentation templateRepresentation = new TemplateRepresentation(template, MediaType.TEXT_HTML);
		configureEngine(templateRepresentation.getEngine());
		return templateRepresentation;
	}

	private static void configureEngine(VelocityEngine engine) {
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        engine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
    }

	public static TemplateRepresentation créeTemplate(String template, Map<String, Object> données) {
		TemplateRepresentation templateRepresentation = créeTemplate(template);
		templateRepresentation.setDataModel(données);
		return templateRepresentation;
	}
}
