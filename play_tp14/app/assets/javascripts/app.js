/*global window,angular*/

/* App Module */
var playFilmApp = angular.module('playFilmApp', [
    'ngRoute',

  'filmControllers',
  'filmServices'
]).run(['$rootScope', 'WebSocket', function($rootScope, WebSocket) {
    $rootScope.search = function () {
      window.location = "#/results";
    };

    setTimeout(function () {
        WebSocket.send({text: "Nous accueillons un nouvel utilisateur!"});
    }, 1000);

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