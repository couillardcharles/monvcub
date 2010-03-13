var uriAppelée;
var contenuARetourner;
$.ajax = function(settings) {
	uriAppelée = settings.url;
	settings.success(contenuARetourner);
};

QUnit.reset = function() {
	localStorage.stations = "";
	$("li").removeClass("selectionnee");
	$("#boutonMenu").show();
	$("#boutonRetour").hide();
	$("#boutonRecharger").show();
}

test("Ajoute bien la classe selectionnee sur les stations préférés", function() {
	localStorage.stations = "1,3";
	selectionnerStations();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
	ok(!$("li[identifiant=2]").hasClass("selectionnee"));
	ok($("li[identifiant=3]").hasClass("selectionnee"));
});

test("Enleve bien la classe selectionnee en cas de rechargement", function() {
	localStorage.stations = "1,3";
	selectionnerStations();
	localStorage.stations = "3";
	selectionnerStations();
	
	ok(!$("li[identifiant=1]").hasClass("selectionnee"));
	ok(!$("li[identifiant=2]").hasClass("selectionnee"));
	ok($("li[identifiant=3]").hasClass("selectionnee"));
});

test("Selectionne bien la station sur le click", function() {
	$("li[identifiant=1] a").click();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
});

test("Garde bien les anciennes selections sur le click d'une autre station", function() {
	localStorage.stations = "2";
	selectionnerStations();
	$("li[identifiant=1] a").click();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
	ok($("li[identifiant=2]").hasClass("selectionnee"));
});

test("Deselectionne bien la station sur le click", function() {
	localStorage.stations = "2";
	selectionnerStations();
	$("li[identifiant=2] a").click();

	equals(localStorage.stations, "");
});

test("Charge bien les stations préférées", function() {
	contenuARetourner = "le contenu";
	localStorage.stations = "1,8";
	chargerMesStations();
	
	equals(uriAppelée,"/mes-stations?stations=1,8");
	equals($("#mes-stations").text(), "le contenu");
});

test("Peut recharger", function() {
	contenuARetourner = "le contenu rechargé";
	localStorage.stations = "3,8";
	
	$("#boutonRecharger a").click();
	
	equals(uriAppelée,"/mes-stations?stations=3,8");
	equals($("#mes-stations").text(), "le contenu rechargé");
});

test("Le bouton retour est caché par défaut", function() {
	ok($("#boutonRetour").is(":hidden"));
}); 


test("Un click sur menu cache les boutons menu et recharger et affiche le bouton retour", function() {
	$("#boutonMenu a").click();
	
	ok($("#boutonMenu").is(":hidden"));
	ok($("#boutonRecharger").is(":hidden"));
	ok($("#boutonRetour").is(":visible"));
	equals($("title").text(), "Stations");
	equals($("#header h1 a").text(), "Stations");
});

test("Un click sur retour cache le bouton retour et affiche menu et recharger", function() {
	$("#boutonMenu a").click();
	
	$("#boutonRetour a").click();
	
	ok($("#boutonMenu").is(":visible"));
	ok($("#boutonRecharger").is(":visible"));
	ok($("#boutonRetour").is(":hidden"));
	equals($("title").text(), "Mes stations");
	equals($("#header h1 a").text(), "Mes stations");
});

test("Un click sur menu affiche bien les stations", function() {
	contenuARetourner = "les stations";
	
	$("#boutonMenu a").click();
	
	equals(uriAppelée,"/stations");
	equals($("#stations").text(), "les stations");
	ok($("#stations").is(":visible"));
	ok($("#mes-stations").is(":hidden"));
});

test("Un click sur menu selectionne bien les stations", function() {
	localStorage.stations = "1,3";
	contenuARetourner = $("#template-stations").html();
	
	$("#boutonMenu a").click();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
	ok(!$("li[identifiant=2]").hasClass("selectionnee"));
	ok($("li[identifiant=3]").hasClass("selectionnee"));
});

test("Un click sur retour affiche bien mes stations", function() {
	localStorage.stations = "1,3";
	contenuARetourner = "mes stations";
	$("#boutonMenu a").click();
	
	$("#boutonRetour a").click();
	
	equals(uriAppelée,"/mes-stations?stations=1,3");
	equals($("#mes-stations").text(), "mes stations");
	ok($("#stations").is(":hidden"));
	ok($("#mes-stations").is(":visible"));
});


