package com.appspot.monvcub.rest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
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
	public void représenteBienLAccueil() throws SAXException, IOException {
		WebResponse accueil = new WebConversation().getResponse("http://localhost:12121");

		assertThat(accueil, notNullValue());
		assertThat(accueil.getTitle(), is("Mes stations"));
		assertThat(accueil.getElementWithID("boutonRecharger"), notNullValue());
		assertThat(accueil.getElementWithID("boutonMenu"), notNullValue());
		assertThat(accueil.getElementWithID("boutonRetour"), notNullValue());
	}

	@Test
	public void représenteBienMesStations() throws IOException, SAXException {
		WebResponse mesStations = new WebConversation().getResponse("http://localhost:12121/mes-stations?stations=1");

		assertThat(mesStations, notNullValue());
		assertThat(mesStations.getLinkWith("Meriadeck"), notNullValue());
		assertThat(mesStations.getElementsWithAttribute("class", "statut"), notNullValue());
	}

	@Test
	public void représenteBienLesStations() throws IOException, SAXException {
		WebResponse stations = new WebConversation().getResponse("http://localhost:12121/stations");

		assertThat(stations, notNullValue());
		assertThat(stations.getLinkWith("Meriadeck"), notNullValue());
		assertThat(stations.getLinkWith("Cauderan"), notNullValue());
	}
}
