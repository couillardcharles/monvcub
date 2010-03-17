package com.appspot.monvcub.stations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.tags.TableRow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserStation {

	public static Station parse(TableRow ligne) {
		Station résultat = new Station();
		résultat.setNom(ligne.getColumns()[1].getStringText().trim());
		résultat.setIdentifiant(Integer.parseInt(ligne.getColumns()[0].getStringText().trim()));

		if (ligne.getColumnCount() == 5) {
			résultat.setEnMaintenance(true);
		} else {
			résultat.setVelosDisponibles(Integer.parseInt(ligne.getColumns()[2].getStringText()));
			résultat.setPlacesDisponibles(Integer.parseInt(ligne.getColumns()[3].getStringText()));
		}
		return résultat;
	}

	public JSONArray parse(String jsonBrut) throws JSONException {
		JSONArray résultat = new JSONArray();
		JSONArray arrayBrut = new JSONArray(jsonBrut);
		for (int i = 0; i < arrayBrut.length(); i++) {
			JSONObject jsonObject = parse(arrayBrut.getJSONObject(i));
			résultat.put(jsonObject);
		}
		return résultat;
	}

	protected JSONObject parse(JSONObject stationJSon) throws JSONException {
		JSONObject résultat = new JSONObject();
		résultat.put("latitude", stationJSon.get("latitude"));
		résultat.put("longitude", stationJSon.get("longitude"));
		String texte = stationJSon.get("text").toString();
		System.out.println(texte);
		résultat.put("titre", getNomStation(texte));
		résultat.put("adresse", getAdresseStation(texte));
		return résultat;
	}

	private String getAdresseStation(String texte) {
		return getTexte(texte, PATTERN_ADRESSE_STATION);
	}

	private String getNomStation(String texte) {
		return getTexte(texte, PATTERN_NOM_STATION);
	}

	private String getTexte(String texte, Pattern pattern) {
		Matcher matcher = pattern.matcher(texte);
		matcher.find();
		return matcher.group(1);
	}

	public static final Pattern PATTERN_NOM_STATION = Pattern.compile("#\\d* \\- (.*)</div>\\n.*gmap\\-adresse");
	public static final Pattern PATTERN_ADRESSE_STATION = Pattern.compile("<div class=.*gmap\\-adresse.*>(.*)</div>(<div.*gmap\\-velos|<p>)");
}
