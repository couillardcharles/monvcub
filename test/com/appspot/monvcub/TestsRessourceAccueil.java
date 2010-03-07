package com.appspot.monvcub;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class TestsRessourceAccueil {

	private Component composant;

	@Before
	public void before() throws Exception {
		composant = new Component();
		composant.getServers().add(Protocol.HTTP, 12121);
		composant.getClients().add(Protocol.HTTP);
		MonVCubApplication application = new MonVCubApplication();
		composant.getDefaultHost().attach(application);
		Application.setCurrent(application);
		if (composant.isStopped()) {
			composant.start();
		}
	}

	@After
	public void after() throws Exception {
		composant.stop();
	}

	@Test
	public void représente() throws IOException {
		ClientResource ressource = new ClientResource("http://localhost:12121");
		
		Representation representation = ressource.get();
		
		assertThat(representation.getText(), is("Hello, world!"));
	}
}
