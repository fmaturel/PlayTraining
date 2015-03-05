/*global angular*/

/* Services */
var filmServices = angular.module('filmServices', ['ngResource']);

filmServices.factory('Film', ['$resource',
    function ($resource) {
        'use strict';
        return $resource('films/:filmId', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);

filmServices.factory('Search', ['$resource',
    function ($resource) {
        'use strict';
        return $resource('films/search', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);

filmServices.factory('Order', ['$resource',
    function ($resource) {
        'use strict';
        return $resource('order', {}, {
            query: {method: 'POST', isArray: true}
        });
    }]);