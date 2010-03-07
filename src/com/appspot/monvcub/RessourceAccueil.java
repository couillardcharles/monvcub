package com.appspot.monvcub;

import java.io.IOException;
import java.util.List;

import org.htmlparser.util.ParserException;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class RessourceAccueil extends ServerResource {
	@Get
	public StringRepresentation represente() throws ParserException, IOException {
		StringBuilder builder = new StringBuilder("Nom\t\tVélos Dispos\t\tPlaces Dispos\n");

		for (Station station : new ServiceStations().getStations(getStationsPréférées())) {
			if (station.estEnMaintenance()) {
				builder.append(station.getNom() + " EN MAINTENANCE\n");
			} else {
				builder.append(String.format("%s\t\t%d\t\t%d\n", station.getNom(), station.getVélosDisponibles(), station
						.getPlacesDisponibles()));
			}
		}

		return new StringRepresentation(builder.toString());
	}

	private List<String> getStationsPréférées() {
		return Lists.newArrayList("Cauderan", "Grand Lebrun", "Barriere St Genes", "Bergonie");
	}
}
