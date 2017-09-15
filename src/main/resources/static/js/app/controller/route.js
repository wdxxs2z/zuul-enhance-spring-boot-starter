'use strict';

angular.module('routeApp')
    .controller('routeCtrl', function ($scope, $location, $http, $state, dataService, $filter) {
    	
        var assignAllRouteDataToScope = function (data) {
            $scope.routeDataArray = data;
        };
               
        var assignRouteDataToScope = function (data) {
            $scope.routeData = data;
        };

        var logError = function (error) {
            console.log(error);
        };
        
        $scope.booleanChose = [
    	    {value: true, text: 'true'},
    	    {value: false, text: 'false'}
    	];
        
        $scope.sensitiveHeadersChose = [
        	{value: 'Cookie', text: 'Cookie'},
        	{value: 'Set-Cookie', text: 'Set-Cookie'},
        	{value: 'Authorization', text: 'Authorization'}
        ]
        
        $scope.showStripPrefix = function(route) {
            var selected = [];
            selected = $filter('filter')($scope.booleanChose, {value: route.stripPrefix});
            return selected.length ? selected[0].text : 'Not set';
        };
        
        $scope.showRetryable = function(route) {
            var selected = [];
            selected = $filter('filter')($scope.booleanChose, {value: route.retryable});
            return selected.length ? selected[0].text : 'Not set';
        };
        
        $scope.showSensitiveHeaders = function(route) {
        	var selected = [];
        	var r = route.sensitiveHeaders.toString().split(",");
        	angular.forEach($scope.sensitiveHeadersChose, function(s) { 
        	      if (r.indexOf(s.value) >= 0) {
        	        selected.push(s.text);
        	      }
        	});
        	return selected.length ? selected.join(',') : '';
        };
        
        $scope.saveRoute = function(id, route) {
            return dataService.updateRoute(id, route);
        };

        $scope.removeRoute = function(id) {
        	 dataService.deleteOneRouteById(id);
        	 $state.reload();
        };
      
        $scope.submitRouteFrom = function () {
        	var data = $scope.formData;
        	dataService.submitRouteFrom(data).then(assignRouteDataToScope, logError);
        	$state.go('route');
        };

        this.getRouteDataByRouteId = function (routeId) {
            dataService.getRouteDataByRouteId(routeId)
                .then(assignRouteDataToScope, logError);
        };        
        
        this.deleteOneRouteById = function (routeId) {
        	dataService.deleteOneRouteById(routeId);
        };
        
        dataService.getAllRouteData().then(assignAllRouteDataToScope, logError);
    });