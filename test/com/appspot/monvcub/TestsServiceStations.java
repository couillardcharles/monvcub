package com.appspot.monvcub;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.htmlparser.util.ParserException;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class TestsServiceStations {

	@Before
	public void before() throws ParserException, IOException {
		stations = new ServiceStations().getStations();
	}

	@Test
	public void peutRécupérerListeStations() {
		assertThat(stations, notNullValue());
		assertThat(stations.size(), is(141));
	}

	@Test
	public void peutRécupérerLeDétailDesStations() {
		Station station = stations.get(0);

		assertThat(station.getNom(), is("Meriadeck"));
		assertThat(station.getVélosDisponibles() + station.getPlacesDisponibles(), is(20));
	}
	
	@Test
	public void peutFiltrerLesStations() throws ParserException, IOException {
		List<String> stationsPréférées = Lists.newArrayList("Cauderan", "Meriadeck");
		
		List<Station> stations = new ServiceStations().getStations(stationsPréférées);
		
		assertThat(stations.size(), is(2));
		assertThat(stations.get(0).getNom(), is("Meriadeck"));
		assertThat(stations.get(1).getNom(), is("Cauderan"));
	}

	private List<Station> stations;
}
