package com.appspot.monvcub.stations;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class TestsParserStation {

	@Test
	public void peutParserStationJSON() throws JSONException {
		String json = "{\"text\":\"<div class='gmap-popup'div class='gmap-infobulle' class='gmap-titre'#1 - Meriadeck</div>\\n<div class='gmap-adresse'>RUE CLAUDE BONNIER FACE À LA STATION </div><div> class='gmap-velos'<table><tr><td> class='alert'<strong>0</strong> vélos disponibles</td>\\n<td> class='ok'<strong>20</strong> places disponibles<td>acronym title='Carte Bancaire'CB/acronym/td/tr/table/divdiv class='gmap-datemaj'dernière mise à jour il y a <strong>00 min</strong></td></tr></table>  /div/div/div\",\"markername\":\"vcub\",\"longitude\":\"-0.58417\",\"latitude\":\"44.83805\"}";
		JSONObject stationJSon = new JSONObject(json);

		JSONObject résultat = new ParserStation().parse(stationJSon);

		assertThat(résultat.get("latitude").toString(), is("44.83805"));
		assertThat(résultat.get("longitude").toString(), is("-0.58417"));
		assertThat(résultat.get("titre").toString(), is("Meriadeck"));
		assertThat(résultat.get("identifiant").toString(), is("1"));
		assertThat(résultat.get("adresse").toString(), is("RUE CLAUDE BONNIER FACE À LA STATION "));
		assertThat(résultat.get("velos").toString(), is("0"));
		assertThat(résultat.get("places").toString(), is("20"));
	}

}
