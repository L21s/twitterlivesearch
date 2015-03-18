function onMessage(event) {
	var tweet = JSON.parse(event.data);
	if (tweet.action === "new_tweet") {
		printSearchKeyword(tweet.text);
	}
}


function addSearchKeyword(query) {
	var socket = new WebSocket("ws://" + document.location.host + "/twitterStream/tweet");

	socket.onmessage = onMessage;
	socket.onopen = function() {
		var SearchQuery = {
			action : "add",
			name : query
		};
		socket.send(JSON.stringify(SearchQuery));
	}

}

function printSearchKeyword(tweet) {
	var searchQueries = document.getElementById("tweets");

	var newQuery = document.createElement("li");
	newQuery.innerHTML = tweet;
	searchQueries.appendChild(newQuery);
}

function formSubmit() {
	var form = document.getElementById("formular")
	var query = form.elements["formular:search"].value;
	addSearchKeyword(query);
	//document.getElementById("formular:search").reset();

}