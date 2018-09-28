(function() {
	var application = angular.module('application', [ 'ngRoute']);

	// Handle routes
	application.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : '/view/records'
		}).when('/event', {
			templateUrl : '/view/event'
		}).when('/events', {
			templateUrl : '/view/events'
		}).when('/records', {
		    templateUrl : '/view/records'
	    }).otherwise({
			redirectTo : '/'
		});
	} ]);
})();
