wmttEditLayout = null;

document.onmousemove = updateTTEditLayout;

function updateTTEditLayout(e) {
	// - 180px because of fixed positioning in edit layout
	x = (document.all) ? window.event.x + document.body.scrollLeft : e.pageX;
	y = (document.all) ? window.event.y + document.body.scrollTop : e.pageY;
	if (wmttEditLayout != null) {
		wmttEditLayout.style.left = (x + 20) + "px";
		wmttEditLayout.style.top = (y + 10) + "px";
	}
}

function showTT(id) {
	wmttEditLayout = document.getElementById(id);
	wmttEditLayout.style.display = "block"
}

function hideTT() {
	wmttEditLayout.style.display = "none";
}
