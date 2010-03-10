$(function() {
	charger();
	$("#boutonRecharger a").click(function() {
		charger();
	});
});

function charger() {
	$("#mes-stations").html($("#template-loader").html());
	localStorage.stations = (localStorage.stations || "");
	$.get("/mes-stations?stations=" + localStorage.stations, function(data) {
		$("#mes-stations").html(data);
	});
};