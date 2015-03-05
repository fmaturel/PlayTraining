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
        return $resource('order', {}, {
            query: {method: 'POST', isArray: true}
        });
    }]);

filmServices.factory('WebSocket', ['$q', '$rootScope', function ($q, $rootScope) {
    // We return this object to anything injecting our service
    var Service = {};
    // Keep all pending requests here until they get responses
    var callbacks = {};
    // Create a unique callback ID to map requests to responses
    var currentCallbackId = 0;
    // Create our websocket object with the address to the websocket
    var ws = new WebSocket("ws://" + location.host + "/ws");

    ws.onopen = function () {
        console.log("Socket has been opened!");
    };

    ws.onmessage = function (message) {
        listener(JSON.parse(message.data));
    };

    function listener(data) {
        var incoming = data;
        console.log("Received data from websocket: ", incoming);

        $.Notify({
            style: {background: '#222', color: 'white'},
            caption: 'Nouveau message :',
            content: incoming.message,
            timeout: 10000 // 10 seconds
        });

        // If an object exists with callback_id in our callbacks object, resolve it
        if (callbacks.hasOwnProperty(incoming.callback_id)) {
            console.log(callbacks[incoming.callback_id]);
            $rootScope.$apply(callbacks[incoming.callback_id].cb.resolve(incoming.data));
            delete callbacks[incoming.callbackID];
        }
    }

    // This creates a new callback ID for a request
    function getCallbackId() {
        currentCallbackId += 1;
        if (currentCallbackId > 10000) {
            currentCallbackId = 0;
        }
        return currentCallbackId;
    }

    Service.send = function (request) {
        var defer = $q.defer();
        var callbackId = getCallbackId();
        callbacks[callbackId] = {
            time: new Date(),
            cb: defer
        };
        request.callback_id = callbackId;
        console.log('Sending request', request);
        ws.send(JSON.stringify(request));
        return defer.promise;
    };

    return Service;
}]);
