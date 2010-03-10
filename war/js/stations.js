$(function() {
	selectionnerStations();
	$("li a").click(function(e) {
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
});

function selectionnerStations() {
	$("li").removeClass("selectionnee");
	if (localStorage.stations != undefined) {
		var stations = localStorage.stations.split(",");
		for ( i = 0; i < stations.length; i++) {
			$("li[identifiant="+ stations[i] +"]").addClass("selectionnee");
		}
	}
};

function getIndex(tableau, element) {
	for (var i = 0; i < tableau.length; i++) {
		if (tableau[i] == element) {
			return i;
		}
	}
	return -1;
};