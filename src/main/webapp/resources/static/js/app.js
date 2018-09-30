(function() {
	var application = angular.module('application', [ 'ngRoute']);

	// Handle routes
	application.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : '/view/remainingVisits'
		}).when('/task', {
			templateUrl : '/view/task'
		}).when('/doneTasks', {
			templateUrl : '/view/doneTasks'
		}).when('/remainingVisits', {
		    templateUrl : '/view/remainingVisits'
	    }).when('/allTasks', {
			templateUrl : '/view/allTasks'
		}).otherwise({
			redirectTo : '/'
		});
	} ]);
})();
