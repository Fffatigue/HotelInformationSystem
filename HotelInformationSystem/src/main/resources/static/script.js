// script.js

// create the module and name it app
// also include ngRoute for all our routing needs
var app = angular.module('hotelInformationSystemApp', ['ngRoute']);

// configure our routes
/*здесь идет роутинг страниц, страницы всегда начинаются с #, типа если перейдем например на #/test/ то роутинг должен
* быть .when('test'), в роутинге задается какая страница должна открываться и инициализация контроллера, но контроллерами
* я не пользовался, потому что чото сложно, я просто в локал сторейдж добавлял нужные для перехода переменные*/
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
        .when('/buildings/:pageNum', {
            templateUrl: 'pages/buildings.html',
            controller: 'buildingsController'
        })
        .when('/organizations/:pageNum', {
            templateUrl: 'pages/organizations.html',
            controller: 'organizationsController'
        })
        .when('/reservations/:pageNum', {
            templateUrl: 'pages/reservations.html',
            controller: 'reservationsController'
        })
        //здесь роутинг с параметрами параметры обозначаются :param
        .when('/building/:buildingId/floor/:floorNum/room/:roomNum', {
            templateUrl: 'pages/room.html',
            controller: 'roomController'
        })
        .when('/building/:buildingId', {
            templateUrl: 'pages/building.html',
            controller: 'buildingController'
        })
        .when('/organization/:organizationId', {
            templateUrl: 'pages/organization.html',
            controller: 'organizationController'
        })
        .when('/report/room/2',{
            templateUrl :'pages/report/room2.html'
        })
        .when('/report/room/3',{
            templateUrl :'pages/report/room3.html'
        })
        .when('/report/client/1',{
            templateUrl :'pages/report/client1.html'
        })
        .when('/report/organization/1',{
            templateUrl :'pages/report/organization1.html'
        })

        // route for the contact page
        .when('/contact', {
            templateUrl: 'pages/contact.html',
            controller: 'contactController'
        }).when('/report/room/1',{
            templateUrl :'pages/report/room1.html'
    });
});

//$routeParams - объект, полями которого являются переданные в строке параметры, я их просто добавлял в локал сторейдж,
//а потом доставал на странице, там где они мне были нужны
app.controller('roomsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});
app.controller('floorsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});

app.controller('buildingsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});

app.controller('organizationsController', function ($scope, $routeParams) {
    localStorage.setItem("page", $routeParams.pageNum);
});

app.controller('roomController', function ($scope, $routeParams) {
    $scope.buildingId = $routeParams.buildingId;
    localStorage.setItem("buildingId", $routeParams.buildingId);
    localStorage.setItem("floorNum", $routeParams.floorNum);
    localStorage.setItem("roomNum", $routeParams.roomNum);
});

app.controller('buildingController', function ($scope, $routeParams) {
    $scope.buildingId = $routeParams.buildingId;
    localStorage.setItem("buildingId", $routeParams.buildingId);
});

app.controller('organizationController', function ($scope, $routeParams) {
    $scope.organizationId = $routeParams.organizationId;
    localStorage.setItem("organizationId", $routeParams.organizationId);
});

app.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});