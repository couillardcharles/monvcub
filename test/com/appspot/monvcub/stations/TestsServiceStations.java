package com.appspot.monvcub.stations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class TestsServiceStations {

	@Before
	public void before() throws ParserException {
		stations = new ServiceStations().getStations();
	}

	@Test
	public void peutRécupérerListeStations() {
		assertThat(stations, notNullValue());
		assertThat(stations.size(), is(139));
	}

	@Test
	public void peutRécupérerLeDétailDesStations() {
		final Station station = stations.get(stations.size() - 1);

		assertThat(station.getNom(), is("Xaintrailles"));
		assertThat(station.getVelosDisponibles() + station.getPlacesDisponibles(), is(15));
		assertThat(station.getIdentifiant(), is(26));
	}

	@Test
	public void peutFiltrerLesStationsEtLesOrdonner() throws ParserException, IOException {
		final List<Integer> stationsPréférées = Lists.newArrayList(1, 31);

		final List<Station> stations = new ServiceStations().getStations(stationsPréférées);

		assertThat(stations.size(), is(2));
		assertThat(stations.get(0).getNom(), is("Cauderan"));
		assertThat(stations.get(1).getNom(), is("Meriadeck"));
	}

	@Test
	public void peutRécupérerLesStationsJson() throws JSONException {
		final JSONArray stationsJson = new ServiceStations().getStationsJson();

		assertThat(stationsJson, notNullValue());
		assertThat(stationsJson.length(), is(139));
	}

	private List<Station> stations;
}
