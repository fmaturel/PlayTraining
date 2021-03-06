/*global window,angular*/

/* App Module */
var playFilmApp = angular.module('playFilmApp', [
    'ngRoute',

    'filmControllers',
    'filmServices'
]).run(function ($rootScope) {
    'use strict';
    $rootScope.search = function () {
        window.location = "#/results";
    };
});

playFilmApp.config(['$routeProvider',
    function ($routeProvider) {
        'use strict';
        $routeProvider.
            when('/highlight', {
                templateUrl: 'assets/partials/film-highlight.html',
                controller: 'FilmHighlightCtrl'
            }).
            when('/films', {
                templateUrl: 'assets/partials/film-list.html',
                controller: 'FilmListCtrl'
            }).
            when('/results', {
                templateUrl: 'assets/partials/film-list.html',
                controller: 'SearchCtrl'
            }).
            when('/films/:filmId', {
                templateUrl: 'assets/partials/film-detail.html',
                controller: 'FilmDetailCtrl'
            }).
            when('/cart', {
                templateUrl: 'assets/partials/cart.html',
                controller: 'CartCtrl'
            }).
            otherwise({
                redirectTo: '/highlight'
            });
    }]);