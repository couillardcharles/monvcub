package com.appspot.monvcub.rest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class TestsRessources {

	private Component composant;

	@Before
	public void before() throws Exception {
		composant = new Component();
		composant.getServers().add(Protocol.HTTP, 12121);
		composant.getClients().add(Protocol.HTTP);
		final MonVCubApplication application = new MonVCubApplication();
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

	public void représenteBienLAccueil() throws SAXException, IOException {
		final WebResponse accueil = new WebConversation().getResponse("http://localhost:12121");

		assertThat(accueil, notNullValue());
		assertThat(accueil.getTitle(), is("Mes stations"));
		assertThat(accueil.getElementWithID("boutonMenu"), notNullValue());
		assertThat(accueil.getElementWithID("boutonRetour"), notNullValue());
	}

	public void représenteBienMesStations() throws IOException, SAXException {
		final WebResponse mesStations = new WebConversation()
				.getResponse("http://localhost:12121/mes-stations?stations=1");

		assertThat(mesStations, notNullValue());
		assertThat(mesStations.getLinkWith("Meriadeck"), notNullValue());
		assertThat(mesStations.getElementsWithAttribute("class", "statut"), notNullValue());
	}

	public void représenteBienLesStations() throws IOException, SAXException {
		final WebResponse stations = new WebConversation().getResponse("http://localhost:12121/stations");

		assertThat(stations, notNullValue());
		assertThat(stations.getLinkWith("Meriadeck"), notNullValue());
		assertThat(stations.getLinkWith("Cauderan"), notNullValue());
	}

	public void peutRécupérerLesInformationsDeLocalisation() {
		final ClientResource ressource = new ClientResource("http://localhost:12121/stations.json");

		ressource.get();

		assertThat(ressource.getStatus(), is(Status.SUCCESS_OK));
	}

	public void représenteBienLePlan() throws IOException, SAXException {
		final WebResponse plan = new WebConversation().getResponse("http://localhost:12121/stations");

		assertThat(plan, notNullValue());
	}

}
