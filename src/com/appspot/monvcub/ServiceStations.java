package com.appspot.monvcub;

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

	protected List<Station> getStations() throws IOException, ParserException {
		List<Station> résultat = Lists.newArrayList();

		ClientResource ressource = new ClientResource("http://www.vcub.fr/stations/detail/nom/");
		Representation representation = ressource.get();
		Parser parser = Parser.createParser(representation.getText(), "UTF-8");
		NodeList tables = parser.parse(new CssSelectorNodeFilter(".stations-list"));
		TableTag table = (TableTag) tables.elementAt(0);

		for (TableRow ligne : table.getRows()) {
			if (!ligne.getStringText().contains("<th>")) {
				résultat.add(ParserStation.parse(ligne));
			}
		}

		return résultat;
	}

	public List<Station> getStations(final List<String> stationsPréférées) throws ParserException, IOException {
		List<Station> résultat = Lists.newArrayList(Iterables.filter(getStations(), new Predicate<Station>() {	
			@Override
			public boolean apply(Station station) {
				return stationsPréférées.contains(station.getNom());
			}
		}));
		Collections.sort(résultat, new Comparator<Station>() {
			@Override
			public int compare(Station o1, Station o2) {
				return o1.getNom().compareTo(o2.getNom());
			}
		});
		return résultat;
	}

}
