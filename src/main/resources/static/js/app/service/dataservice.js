'use strict';

angular.module('routeApp')
    .factory('dataService', function ($http, $q) {

        var routeApi = '/route';
        
        var makeRestCaller = function (url, method, data) {
        	switch (method){
        	case 'GET' :
        		return $http.get(url).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'POST' :
        		var d = data;
        		return $http.post(url, data).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'DELETE' : 
        		return $http.delete(url).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'PUT' :
        		return $http.put(url,data).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	}
        }

        return {
            getAllRouteData: function () {	
                return makeRestCaller(routeApi, 'GET', '');
            },

            getRouteDataByRouteId: function (routeId) {        
                return makeRestCaller(routeApi + '/' + routeId, 'GET', '');
            },
            
            submitRouteFrom: function(data){
            	return makeRestCaller(routeApi, 'POST', data);
            },
            
            updateRoute: function(routeId, data){
            	return makeRestCaller(routeApi + '/' + routeId, 'PUT', data);
            },
            
            deleteOneRouteById: function(routeId){
            	return makeRestCaller(routeApi + '/' + routeId, 'DELETE', '');
            }
        };
    });