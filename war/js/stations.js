$(function() {
	$("#boutonRetour").hide();
	chargerMesStations();
	$("#stations li a").live("click", function(e) {
		var li = $(e.target).closest("li");
		var identifiant = li.attr("identifiant");
		localStorage.stations = (localStorage.stations || "");
		var stationsSelectionnees = localStorage.stations.split(",");
		
		var index = getIndex(stationsSelectionnees, identifiant);
		if (index != -1) {
			stationsSelectionnees.splice(index, 1);
		} else {
			stationsSelectionnees.push(identifiant);
		}
		localStorage.stations = stationsSelectionnees.join(",");
		selectionnerStations();
		return false;
	});
	$("#boutonRecharger a").click(function() {
		chargerMesStations();
	});
	$("#boutonMenu a").click(function() {
		toggle("Stations");
		chargerStations();
		return false;
	});
	$("#boutonRetour a").click(function() {
		toggle("Mes stations");
		chargerMesStations();
		return false;
	});
});

function toggle(titre) {
	$("#boutonRetour").toggle();
	$("#boutonMenu").toggle();
	$("#boutonRecharger").toggle();
	$("title").text(titre);
	$("#header h1 a").text(titre)
}

function selectionnerStations() {
	$("li").removeClass("selectionnee");
	if (localStorage.stations != undefined) {
		var stations = localStorage.stations.split(",");
		for ( i = 0; i < stations.length; i++) {
			$("li[identifiant="+ stations[i] +"]").addClass("selectionnee");
		}
	}
};

function chargerMesStations() {
	$("#stations").hide();
	$("#mes-stations").html($("#template-loader").html()).show();
	localStorage.stations = (localStorage.stations || "");
	$.get("/mes-stations?stations=" + localStorage.stations, function(data) {
		$("#mes-stations").html(data);
	});
};

function chargerStations() {
	$("#mes-stations").hide();
	$("#stations").html($("#template-loader").html()).show();
	$.get("/stations", function(data) {
		$("#stations").html(data);
		selectionnerStations();
	});
}

function getIndex(tableau, element) {
	for (var i = 0; i < tableau.length; i++) {
		if (tableau[i] == element) {
			return i;
		}
	}
	return -1;
};