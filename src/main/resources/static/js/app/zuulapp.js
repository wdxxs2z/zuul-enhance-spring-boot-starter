'use strict';

angular.module('routeApp', ['ui.router','xeditable','ui.select','ngSanitize','ui.bootstrap','ui.date','ngTagsInput'])
.run(function(editableOptions){
	editableOptions.theme = 'bs3';
})    
.config(function ($stateProvider, $httpProvider, $urlRouterProvider) {
    	$urlRouterProvider.otherwise('/');
    	$stateProvider
    	.state('route', {
            url: '/',
    		templateUrl: 'view/route.list.html',
            controller: 'routeCtrl',
            controllerAs: 'routeController'
        }).state('newRoute', {
            url: '/newroute',
        	templateUrl: 'view/route.newroute.html',
            controller: 'routeCtrl',
            controllerAs: 'routeController'
        });
    });