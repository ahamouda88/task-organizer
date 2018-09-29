(function() {
	var application = angular.module('application', [ 'ngRoute']);

	// Handle routes
	application.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : '/view/remainingVisits'
		}).when('/task', {
			templateUrl : '/view/task'
		}).when('/doneVisits', {
			templateUrl : '/view/doneVisits'
		}).when('/remainingVisits', {
		    templateUrl : '/view/remainingVisits'
	    }).otherwise({
			redirectTo : '/'
		});
	} ]);
})();
