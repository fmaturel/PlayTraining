/**
 * Created by fmaturel on 2/16/14.
 */

'use strict';

/* Controllers */

var filmControllers = angular.module('filmControllers', []);

var getCart = function () {
  return angular.fromJson(sessionStorage.getItem("cart")) || {"items": []};
};

var getItem = function (items, filmId) {
  var item = null;
  angular.forEach(items, function (value) {
    if (value.id === filmId) {
      item = value;
    }
  });
  return item;
};

filmControllers.controller('SearchCtrl', ['$scope', '$rootScope', 'Search',
  function ($scope, $rootScope, Search) {
    $scope.films = Search.query({q: $rootScope.query});
  }]);

filmControllers.controller('FilmHighlightCtrl', ['$scope', 'Film',
  function ($scope, Film) {
    $scope.films = Film.query();
  }]);

filmControllers.controller('FilmListCtrl', ['$scope', '$rootScope', 'Film',
  function ($scope, $rootScope, Film) {
    $scope.films = Film.query({size: 100});
  }]);

filmControllers.controller('FilmDetailCtrl', ['$scope', '$routeParams', 'Film',
  function ($scope, $routeParams, Film) {
    $scope.film = Film.get({filmId: $routeParams.filmId}, function (film) {
      $scope.film.url = "url('assets/images/" + film.titre + ".jpg')";
    });
  }]);

filmControllers.controller('CartCtrl', ['$scope', 'Film', 'Order',
  function ($scope, Film, Order) {

    $scope.items = getCart().items;

    $scope.submit = function () {
      var filmId = $scope.film.id;
      var items = $scope.items || getCart().items;
      var item = getItem(items, filmId);
      var callback = function (film) {
        if (film) {
          delete film.id;
        }
        sessionStorage.setItem("cart", angular.toJson({items: items}));
        window.location = "#/cart";
      };

      if (item) {
        item.nb = item.nb + 1;
        callback();
      } else {
        items.push({"id": filmId, "nb": 1, "film": Film.get({filmId: filmId}, callback)});
      }
    };

    $scope.order = function () {
      var cart = getCart(), order={};

      angular.copy(cart, order);

      angular.forEach(order.items, function (value) {
        delete value.film;
      });

      if(order.items.length) {
        Order.query(order, function () {

          var titres = [];
          angular.forEach(cart.items, function (value) {
            titres.push(value.film.titre);
          });

          $scope.empty();
          $scope.message = {"type": "success", "text": ""};

          $.Notify({
            style: {background: 'green', color: 'white'},
            caption: 'Pour info...',
            content: "Vos films ont bien été commandé!",
            timeout: 10000 // 10 seconds
          });
        }, function () {
          console.error("Erreur pendant la commande!");

          $.Notify({
            style: {background: 'red', color: 'white'},
            caption: 'Oops...',
            content: "Vos films n'ont pas pas être commandé...",
            timeout: 10000 // 10 seconds
          });
        });
      } else {
        $.Notify({
          style: {background: 'orange', color: 'white'},
          caption: 'Mmm...',
          content: "Je ne trouve rien dans votre panier.",
          timeout: 5000 // 10 seconds
        });
      }
    };

    $scope.empty = function () {
      sessionStorage.removeItem("cart");
      $scope.items.length = 0;
    };

    $scope.remove = function (item) {
      $scope.items.splice($scope.items.indexOf(item), 1);
      sessionStorage.setItem("cart", angular.toJson({items: $scope.items}));
    };
  }]);