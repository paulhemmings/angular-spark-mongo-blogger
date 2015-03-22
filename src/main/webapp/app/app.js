/*
 * Main Angular module
 *
 * Style guide:
 * avoid polluting global namespace:
 *  var app = angular.module('app');
 */

angular.module('MainApplicationModule', ['ngRoute', 'ngAnimate', 'angular-loading-bar']);

/*
 * Add SPA Routing using route provider
 *
 * Style guide:
 * avoid using a variable and instead use chaining with the getter syntax
 *
 */

angular
    .module('MainApplicationModule')
    .config(function ($routeProvider) {
        $routeProvider
            .when('/blogger',
            {
                controller: 'BloggerController',
                templateUrl: '/app/partials/blogger.html'
            })
            .otherwise({ redirectTo: '/blogger' });
    });