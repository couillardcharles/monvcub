package com.appspot.monvcub.rest;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import freemarker.template.Configuration;

public class MonVCubApplication extends Application {
	@Override
	public synchronized void start() throws Exception {
		configureFreemarker();
		super.start();
	}

	@Override
	public Restlet createInboundRoot() {
		final Router router = new Router(getContext());

		router.attach("/", RessourceAccueil.class);
		router.attach("/stations", RessourceStations.class);
		router.attach("/stations.json", RessourceStationsJSON.class);
		router.attach("/mes-stations", RessourceMesStations.class);
		router.attach("/plan", RessourcePlan.class);
		return router;
	}

	public ServletContext servletContext() {
		return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
	}

	public static MonVCubApplication getCourante() {
		return (MonVCubApplication) getCurrent();
	}

	public Configuration getConfigurationFreemarker() {
		return configurationFreemarker;
	}

	private void configureFreemarker() {
		try {
			configurationFreemarker = new Configuration();
			configurationFreemarker.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
			configurationFreemarker.setEncoding(Locale.FRANCE, "UTF-8");
		} catch (final Exception e) {

		}
	}

	private Configuration configurationFreemarker;
}
