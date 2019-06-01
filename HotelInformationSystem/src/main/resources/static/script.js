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
        //здесь роутинг с параметрами параметры обозначаются :param
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

//$routeParams - объект, полями которого являются переданные в строке параметры, я их просто добавлял в локал сторейдж,
//а потом доставал на странице, там где они мне были нужны
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