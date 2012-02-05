package com.appspot.monvcub.rest;

import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;

public class FabriqueTemplate {
	public static TemplateRepresentation créeTemplate(final String template) {
		TemplateRepresentation templateRepresentation = new TemplateRepresentation(template, MonVCubApplication
				.getCourante().getConfigurationFreemarker(), MediaType.TEXT_HTML);
		return templateRepresentation;
	}

	public static TemplateRepresentation créeTemplate(final String template, final Map<String, Object> données) {
		TemplateRepresentation templateRepresentation = créeTemplate(template);
		templateRepresentation.setDataModel(données);
		return templateRepresentation;
	}

}
