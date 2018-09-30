(function() {
	var application = angular.module("application");

	// Task Controller
	function TaskController($scope, $rootScope, $location, $filter, TaskService, PagerService) {
		$scope.allTypes = ['WEEKLY', 'DAILY', 'MONTHLY'];

		$scope.setTaskType = function(type) {
			$scope.task.type = type;
		};
		
		// Add or Update Task
		$scope.addUpdateTask = function() {
			var dataObj = {
				version : $scope.task.version,
				id : $scope.task.id,
				from : $filter('date')($scope.task.from, "yyyy-MM-dd"),
				location : $scope.task.location,
				comment : $scope.task.comment,
				name : $scope.task.name,
				numOfVisits : $scope.task.numOfVisits,
				type : $scope.task.type
			};

			// Verify if it is an insert or update request
			if ($scope.task.id === undefined) {
				$scope.insert = true;
			} else {
				$scope.insert = false;
			}

			var response = TaskService.addUpdateTask(dataObj);
			response.success(function(data) {
				$scope.task = {};
				$scope.task.response = data;
				//$location.path("/doneTasks");
			});
			response.error(function(data) {
				$scope.task.response = data;
				$scope.insert = false;
			});
		};

		// Delete Task
		$scope.deleteTask = function(task) {
			if (confirm('Are you sure you want to delete task with Id: ' + task.id + ' ?')) {
				var response = TaskService.deleteTask(task).then(function() {
					$scope.tasks = $scope.getAllTasks();
					$scope.deletedTask = task;
				});
			}
		};

		// Set target Task, when row is selected
		$scope.setTargetTask = function(task) {
			task.from = new Date(task.from);
			TaskService.setTargetTask(task);
		};

		// Get target Task, to be displayed on the add/update page
		$scope.getTargetTask = function() {
			$scope.task = TaskService.getTargetTask();
			var emptyTask = {};
			TaskService.setTargetTask(emptyTask);
		};

		// Get All Tasks
		$scope.getAllTasks = function() {
			var response = TaskService.getAllTasks();
			response.success(function(data) {
				$scope.tasks = data;
				setUpPagination(data);
			});
		};

		// Get Done Tasks
		$scope.getDoneTasks = function() {
			var response = TaskService.getDoneTasks();
			response.success(function(data) {
				$scope.tasks = data;
				setUpPagination(data);
			});
		};

		// Get Remaining Visits
		$scope.getRemainingVisits = function() {
			var response = TaskService.getRemainingVisits();
			response.success(function(data) {
				$scope.visits = data;
				setUpPagination(data);
			});
		};

		$scope.initTaskPage = function() {
			$scope.getTargetTask();
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

	application.controller('TaskController', TaskController);
})();