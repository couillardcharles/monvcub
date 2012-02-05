$(function() {
	chargerMesStations();
	$("#boutonRechargement").live("click", function() {
		chargerMesStations();
	});
	$(".page-stations li a").live("click", function(e) {
		var li = $(e.target).closest("li");
		ajouteSupprimeStation(li.attr("identifiant"));
		selectionnerStations();
		return false;
	});
	$(".page-plan .ajout-station").live("click", function(e){
		var identifiant = $(e.target).attr("identifiant");
		ajouteSupprimeStation(identifiant);
		$(e.target).closest("p").text("Station sélectionnée").css("color", "#EB6100");
	});
	$('.page-stations').live('pageshow',function(){
		selectionnerStations();
	});
	$('.page-mes-stations').live('pageshow',function(){
		chargerMesStations();
	});
	$('.page-plan').live('pageshow',function(){
		chargerCarte();
	});
});

function chargerMesStations() {
	$.mobile.showPageLoadingMsg();	
	localStorage.stations = (localStorage.stations || "");
	$.get("/mes-stations?stations=" + localStorage.stations, function(data) {
		$(".page-mes-stations ul").html(data).listview("refresh");
		$.mobile.hidePageLoadingMsg();	
	});
};

function ajouteSupprimeStation(identifiant) {
	var index = indexStation(identifiant);
	var stationsSelectionnees = localStorage.stations.split(",");
	if (index != -1) {
		stationsSelectionnees.splice(index, 1);
	} else {
		stationsSelectionnees.push(identifiant);
	}
	localStorage.stations = stationsSelectionnees.join(",");
}

function indexStation(identifiant) {
	localStorage.stations = (localStorage.stations || "");
	var stationsSelectionnees = localStorage.stations.split(",");
	
	return $.inArray(identifiant, stationsSelectionnees);
}

function selectionnerStations() {
	$(".page-stations ul li").removeClass("selectionnee");
	if (localStorage.stations != undefined) {
		var stations = localStorage.stations.split(",");
		for (var i = 0; i < stations.length; i++) {
			$(".page-stations ul li[identifiant="+ stations[i] +"]").addClass("selectionnee");
		}
	}
};

function chargerCarte() {
	var latlng = new google.maps.LatLng(44.835178,-0.577126);
	var myOptions = {zoom: 12, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP, mapTypeControlOptions : {mapTypeIds: []}};
	var map = new google.maps.Map(document.getElementById("carte"), myOptions);
	$.getJSON('/stations.json', function(data) {
		for (var i = 0; i < data.length; i++) {
			créerMarker(data[i], map);
		}
	});
	
	getGeoLocalisation(map);
}

function créerMarker(station, map) {
	var infoStation = $("<div>");
	if (indexStation(station.identifiant) == -1) {
		infoStation.append($("<p>").append($("<button class='ajout-station' identifiant='" + station.identifiant + "'>").text("Ajouter à mes stations")));
	} else {
		infoStation.append($("<p>").text("Station déjà sélectionnée").css("color", "#EB6100"));
	}
	
	var infowindow = new google.maps.InfoWindow({
		content: "<h1>" + station.titre + "</h1>" + station.adresse + "<br>Vélos disponibles: " + station.velos + "<br>Places disponibles: " + station.places + "<br>" + infoStation.html()
	});
	var marker = new google.maps.Marker({
        position: new google.maps.LatLng(station.latitude, station.longitude), 
        map: map,
        title: station.titre,
        icon : getIcon(station)
    });
	
	google.maps.event.addListener(marker, 'click', function() {
		  infowindow.open(map,marker);
	});
}

function getIcon(station) {
	var icon = '../images/m_vert.png';
	if (station.places == "0" && station.velos == "0") {
		icon = '../images/m_gris.png'; 
	} else if (station.places == "0") {
		icon = '../images/m_orange.png';
	} else if (station.velos == "0") {
		icon = '../images/m_rouge.png';
	}
	return icon;
}

function getGeoLocalisation(map) {
	navigator.geolocation.getCurrentPosition(LocationOK);

	function LocationOK(position)
	{
	  new google.maps.Marker({
	        position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
	        map: map,
	        icon: '../images/flag.png'
	    });
	}
}
