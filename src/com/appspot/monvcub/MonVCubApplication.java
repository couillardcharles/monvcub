package com.appspot.monvcub;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MonVCubApplication extends Application {
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attachDefault(RessourceAccueil.class);

		return router;
	}
}
