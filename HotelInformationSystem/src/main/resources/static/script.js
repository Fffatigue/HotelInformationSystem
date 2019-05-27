// script.js

// create the module and name it app
// also include ngRoute for all our routing needs
var app = angular.module('hotelInformationSystemApp', ['ngRoute']);

// configure our routes
app.config(function ($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/', {
            templateUrl: 'pages/home.html'
        })

        // route for the about page
        .when('/rooms/:pageNum', {
            templateUrl: 'pages/rooms.html',
            controller: 'roomsController'
        })
        .when('/floors/:pageNum', {
            templateUrl: 'pages/floors.html',
            controller: 'floorsController'
        })
        .when('/building/:buildingId/floor/:floorNum/room/:roomNum', {
            templateUrl: 'pages/room.html',
            controller: 'roomController'
        })

        // route for the contact page
        .when('/contact', {
            templateUrl: 'pages/contact.html',
            controller: 'contactController'
        });
});

app.controller('roomsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});
app.controller('floorsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});


app.controller('roomController', function ($scope, $routeParams) {
    $scope.buildingId = $routeParams.buildingId;
    localStorage.setItem("buildingId", $routeParams.buildingId);
    localStorage.setItem("floorNum", $routeParams.floorNum);
    localStorage.setItem("roomNum", $routeParams.roomNum);
});


app.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});