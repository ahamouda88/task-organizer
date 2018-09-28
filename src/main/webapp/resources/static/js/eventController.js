(function() {
	var application = angular.module("application");

	// Event Controller
	function EventController($scope, $rootScope, $location, EventService, PagerService) {
		$scope.allTypes = ['WEEKLY', 'DAILY', 'MONTHLY'];

		$scope.setEventType = function(type) {
			$scope.event.type = type;
		};

		// Add or Update Event
		$scope.addUpdateEvent = function() {
			var dataObj = {
				version : $scope.event.version,
				id : $scope.event.id,
				from : $scope.event.from,
				to : $scope.event.to,
				description : $scope.event.description,
				type : ($scope.event.type === undefined ? 'DAILY' : $scope.event.type)
			};

			// Verify if it is an insert or update request
			if ($scope.event.id === undefined) {
				$scope.insert = true;
			} else {
				$scope.insert = false;
			}

			var response = EventService.addUpdateEvent(dataObj);
			response.success(function(data) {
				$scope.event = {};
				$scope.event.response = data;
				//$location.path("/events");
			});
			response.error(function(data) {
				$scope.event.response = data;
				$scope.insert = false;
			});
		};

		// Delete Event
		$scope.deleteEvent = function(event) {
			if (confirm('Are you sure you want to delete event with Id: ' + event.id + ' ?')) {
				var response = EventService.deleteEvent(event).then(function() {
					$scope.events = $scope.getAllEvents();
					$scope.deletedEvent = event;
				});
			}
		};

		// Set target Event, when row is selected
		$scope.setTargetEvent = function(event) {
			EventService.setTargetEvent(event);
		};

		// Get target Event, to be displayed on the add/update page
		$scope.getTargetEvent = function() {
			$scope.event = EventService.getTargetEvent();
			var emptyEvent = {};
			EventService.setTargetEvent(emptyEvent);
		};

		// Get All Events
		$scope.getAllEvents = function() {
			var response = EventService.getAllEvents();
			response.success(function(data) {
				$scope.events = data;
				setUpPagination(data);
			});
		};

		// Get Records
		$scope.getTodaysRecords = function() {
			var response = EventService.getRecords();
			response.success(function(data) {
				$scope.records = data;
				console.log('ahmed: ' + data);
				setUpPagination(data);
			});
		};

		$scope.initEventPage = function() {
			$scope.getTargetEvent();
		};
		
		$scope.setNext = function() {
			$scope.currentPage = $scope.tablePagination.setNext();
		};
		
		$scope.setPrev = function() {
			$scope.currentPage = $scope.tablePagination.setPrev();
		};
		
		// Setting up pagination
		function setUpPagination(data){
			$scope.tablePagination = PagerService.tablePagination(data, 5);
			$scope.currentPage = $scope.tablePagination.currentPage;
		}
	}

	application.controller('EventController', EventController);
})();