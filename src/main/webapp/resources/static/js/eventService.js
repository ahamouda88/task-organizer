(function() {
	var application = angular.module("application"),
		eventsPath = '/v1/api/events',
		recordsPath = eventsPath + '/records';

	// Event Service
	function EventService($http, $filter) {
		// Add or Update Event
		this.addUpdateEvent = function(data) {
			/*
			 * Check if event's id is null then make a post request, otherwise
			 * make a put request
			 */
			if (data.id === undefined) {
				return $http.post(eventsPath, data);
			} else {
				return $http.put(eventsPath, data);
			}
		};
	
		// Remove Event
		this.deleteEvent = function(data) {
			var path = eventsPath + '?id=' + data.id;
			return $http.delete(path);
		};

		// Get All Events
		this.getAllEvents = function() {
			return $http.get(eventsPath);
		};

		// Returns records filtered by the given data
		this.getRecords = function(data){
			var path = recordsPath ;
			if(data !== undefined) path += '?date=' + data.date;
			return $http.get(path);
		};
		
		// Set target event, when row is selected
		var targetEvent = {};
		this.setTargetEvent = function(event){
			targetEvent = event;
		};
		
		// Get target event, to be displayed on the add/update page
		this.getTargetEvent = function(){
			return targetEvent;
		};
	}

	application.service("EventService", EventService);
})();