package com.appspot.monvcub.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.htmlparser.util.ParserException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.appspot.monvcub.stations.ServiceStations;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RessourceMesStations extends ServerResource {

	@Override
	protected void doInit() {
		String stations = getQuery().getFirstValue("stations", "");
		stationsPréférées = Lists.transform(Arrays.asList(stations.split(",")), new Function<String, Integer>() {
			@Override
			public Integer apply(final String valeur) {
				Integer résultat;
				try {
					résultat = Integer.parseInt(valeur);
				} catch (NumberFormatException e) {
					résultat = 0;
				}
				return résultat;
			}
		});
	}

	@Get
	public Representation represente() throws ParserException, IOException {
		Map<String, Object> données = Maps.newHashMap();
		données.put("stations", new ServiceStations().getStations(stationsPréférées));
		return FabriqueTemplate.créeTemplate("mes-stations.ftl", données);
	}

	List<Integer> stationsPréférées;
}
