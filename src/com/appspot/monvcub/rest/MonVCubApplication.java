package com.appspot.monvcub.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class MonVCubApplication extends Application {
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/", RessourceAccueil.class);
		router.attach("/stations", RessourceStations.class);
		router.attach("/mes-stations", RessourceMesStations.class);
		router.attach("/stations/carte", RessourceCarteStations.class);
		return router;
	}
}
