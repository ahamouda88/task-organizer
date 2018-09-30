(function() {
	var application = angular.module("application"),
		tasksPath = '/v1/api/tasks',
		remainingVisits = tasksPath + '/remaining_visits',
		doneTasks = tasksPath + '/done_tasks';

	// Task Service
	function TaskService($http, $filter) {
		// Add or Update Task
		this.addUpdateTask = function(data) {
			/*
			 * Check if task's id is null then make a post request, otherwise
			 * make a put request
			 */
			if (data.id === undefined) {
				return $http.post(tasksPath, data);
			} else {
				return $http.put(tasksPath, data);
			}
		};
	
		// Remove Task
		this.deleteTask = function(data) {
			var path = tasksPath + '?id=' + data.id;
			return $http.delete(path);
		};

		// Get All Tasks
		this.getAllTasks = function() {
			return $http.get(tasksPath);
		};

		// Get Done Tasks
		this.getDoneTasks = function() {
			return $http.get(doneTasks);
		};

		// Get Remaining Visits
		this.getRemainingVisits = function(data){
			var path = remainingVisits;
			if(data !== undefined) path += '?date=' + data.date;
			return $http.get(path);
		};

		// Set target task, when row is selected
		var targetTask = {};
		this.setTargetTask = function(task){
			targetTask = task;
		};

		// Get target task, to be displayed on the add/update page
		this.getTargetTask = function(){
			return targetTask;
		};
	}

	application.service("TaskService", TaskService);
})();