socket = new WebSocket("ws://" + document.location.host
		+ "/twitter4serioussearch_testdriver/tweet");
socket.onmessage = onMessage;

function onMessage(event) {
	var tweet = JSON.parse(event.data);
	if (tweet.action === "new_tweet") {
		printSearchKeyword(tweet.text);
	}
}

function addSearchKeyword(query) {
	var SearchQuery = {
		action : "add",
		name : query
	};
	socket.send(JSON.stringify(SearchQuery));
}

function printSearchKeyword(tweet) {
	var searchQueries = document.getElementById("tweets");

	var newQuery = document.createElement("li");
	newQuery.innerHTML = tweet;
	searchQueries.appendChild(newQuery);
}

function formSubmit() {
	var form = document.getElementById("formular");
	var query = form.elements["search"].value;
	document.getElementById("session_id").innerHTML = "<p>Session registered for query: "
			+ query + "</p>";
	var searchQueries = document.getElementById("tweets");

	addSearchKeyword(query);
	form.reset();

}