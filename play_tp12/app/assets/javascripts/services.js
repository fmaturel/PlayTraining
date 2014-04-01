/**
 * Created by fmaturel on 2/16/14.
 */

'use strict';

/* Services */

var filmServices = angular.module('filmServices', ['ngResource']);

filmServices.factory('Film', ['$resource',
  function($resource){
    return $resource('films/:filmId', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);

filmServices.factory('Search', ['$resource',
  function($resource){
    return $resource('films/search', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);

filmServices.factory('Order', ['$resource',
  function($resource){
    return $resource('order', {}, {
      query: {method:'POST', isArray:true}
    });
  }]);