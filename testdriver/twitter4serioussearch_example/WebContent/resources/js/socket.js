var socket = new WebSocket("ws://localhost:8080/twitterStream/tweet");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "add") {
        printSearchKeyword(device.name);
    }
}

function addSearchKeyword(name) {
    var SearchKeyword = {
        action: "add",
        name: name
    };
    socket.send(JSON.stringify(SearchKeyword));
}

function printSearchKeyword(keyword) {
	var searchQueries = document.getElementById("searchqueries");
	
	var newQuery = document.createElement("li");
	newQuery.innerHTML = keyword;
	searchQueries.appendChild(newQuery);
}

function formSubmit() {
	var form = document.getElementById("formular")
	var tweet = form.elements["formular:search"].value;
	addSearchKeyword(tweet);
	form.elements["formular:search"].reset();
	
}