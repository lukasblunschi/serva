/**
 * Reload page.
 */
function reload() {
	location.reload(true)
}

/**
 * Toggle display of block element.
 * 
 * @param elementId
 *            id of block element to toggle
 */
function toggleDisplay(elementId) {
	var style = document.getElementById(elementId).style;
	style.display = (style.display == 'none') ? 'block' : 'none';
}

/**
 * Toggle state of given element.
 */
function toggleState(elementId) {
	var element = document.getElementById(elementId);
	element.disabled = element.disabled ? false : true;
}

/**
 * Show given div.
 */
function showDiv(divId) {
	document.getElementById(divId).style.display = "block";
}

/*
 * Hide given div.
 */
function hideDiv(divId) {
	document.getElementById(divId).style.display = "none";
}
