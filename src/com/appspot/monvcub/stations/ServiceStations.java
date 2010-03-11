package com.appspot.monvcub.stations;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.google.appengine.repackaged.com.google.common.base.Predicate;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class ServiceStations {

	public List<Station> getStations() throws IOException, ParserException {
		List<Station> résultat = Lists.newArrayList();

		Parser parser = Parser.createParser(getHtmlStations(), "UTF-8");
		NodeList tables = parser.parse(new CssSelectorNodeFilter(".stations-list"));
		TableTag table = (TableTag) tables.elementAt(0);

		for (TableRow ligne : table.getRows()) {
			if (!ligne.getStringText().contains("<th>")) {
				résultat.add(ParserStation.parse(ligne));
			}
		}
		Collections.sort(résultat, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getNom().compareTo(o2.getNom());
			}
		});
		return résultat;
	}

	private String getHtmlStations() throws IOException {
		ClientResource ressource = new ClientResource("http://www.vcub.fr/stations/detail/nom/");
		Representation representation = ressource.get();
		return representation.getText();
	}

	public List<Station> getStations(final List<Integer> stationsPréférées) throws ParserException, IOException {
		List<Station> résultat = Lists.newArrayList(Iterables.filter(getStations(), new Predicate<Station>() {	
			@Override
			public boolean apply(Station station) {
				return stationsPréférées.contains(station.getIdentifiant());
			}
		}));
		return résultat;
	}

}
