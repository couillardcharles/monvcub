package com.appspot.monvcub.stations;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import com.google.appengine.repackaged.com.google.common.base.Predicate;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class ServiceStations {

	public List<Station> getStations() throws ParserException {
		final List<Station> résultat = Lists.newArrayList();

		final Parser parser = Parser.createParser(getHtml("http://www.vcub.fr/stations/detail/nom/"), "UTF-8");
		final NodeList tables = parser.parse(new CssSelectorNodeFilter(".stations-list"));
		final TableTag table = (TableTag) tables.elementAt(0);

		for (final TableRow ligne : table.getRows()) {
			if (!ligne.getStringText().contains("<th>")) {
				résultat.add(ParserStation.parse(ligne));
			}
		}
		Collections.sort(résultat, new Comparator<Station>() {
			@Override
			public int compare(final Station o1, final Station o2) {
				return o1.getNom().compareTo(o2.getNom());
			}
		});
		return résultat;
	}

	private String getHtml(final String uri) {
		final Client client = new Client(Protocol.HTTP);
		final Request request = new Request(Method.GET, uri);
		return client.handle(request).getEntityAsText();
	}

	public List<Station> getStations(final List<Integer> stationsPréférées) throws ParserException {
		final List<Station> résultat = Lists.newArrayList(Iterables.filter(getStations(), new Predicate<Station>() {
			@Override
			public boolean apply(final Station station) {
				return stationsPréférées.contains(station.getIdentifiant());
			}
		}));
		return résultat;
	}

	public JSONArray getStationsJson() throws JSONException {
		final String html = getHtml("http://www.vcub.fr/stations/plan");
		final Matcher matcher = PATTERN.matcher(html);
		matcher.find();
		return new ParserStation().parse(matcher.group(1).replace("\\x3c", "<").replace("\\x3e", ">"));
	}

	public static final Pattern PATTERN = Pattern.compile("\"markers\": (.*)} } } }\\);");
}
