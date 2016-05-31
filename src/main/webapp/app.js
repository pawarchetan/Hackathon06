angular.module("sdsApp", ['ngRoute','sdsApp.mainController','sdsApp.when_Scroll_Ends','sdsApp.services'])

    .config(function($routeProvider) {
    $routeProvider
        .when('/showRecords', {
            templateUrl: 'partials/dbRecords.html',
            controller: 'mainController'
        })
        .otherwise({
            redirectTo: '/showRecords'
        });
});