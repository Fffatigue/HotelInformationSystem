// script.js

// create the module and name it app
// also include ngRoute for all our routing needs
var app = angular.module('hotelInformationSystemApp', ['ngRoute']);

// configure our routes
app.config(function ($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/', {
            templateUrl: 'pages/home.html',
            controller: 'mainController'
        })

        // route for the about page
        .when('/rooms', {
            templateUrl: 'pages/rooms.html'
        })
        .when('/building/:buildingName/floor/:floorNum/room/:roomNum', {
            templateUrl: 'pages/room.html',
            controller: 'roomController'
        })

        // route for the contact page
        .when('/contact', {
            templateUrl: 'pages/contact.html',
            controller: 'contactController'
        });
});

// create the controller and inject Angular's $scope
app.controller('mainController', function ($scope) {
    // create a message to display in our view
    $scope.message = 'Everyone come and see how good I look!';
});


app.controller('roomController', function ($scope, $routeParams) {
    $scope.buildingName = $routeParams.buildingName.toString();
    $scope.floorNum = $routeParams.floorNum;
    $scope.roomNum = $routeParams.roomNum;
});


app.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});