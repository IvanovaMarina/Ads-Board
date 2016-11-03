var app = angular.module("RegUserApp", []);

var ctrl = app.controller("CountriesRegCtrl", function ($scope, $http) {
    $scope.selected = false;
    $scope.getCountries = function () {
        $http.get("/geolocation/countries")
             .then(function(response){
                 $scope.countries = response.data._embedded.countries;
             },function myError(response) {
                 $scope.error = response.statusText;
                 alert("error");
             });

    };

    $scope.getRegions = function () {
        $http.get("/geolocation/countries/" + $scope.selected + "/regions")
             .then(function(response){
                 $scope.regions = response.data._embedded.regions;
             },function myError(response) {
                 $scope.error = response.statusText;
             });
    };

    $scope.response = {};
    $scope.save = function (user, registerForm){
        if(registerForm.$valid){
            $http.post("/users/", user).success(function (answ) {
                $scope.response = answ;
            });
        }
    };

    $scope.getCountries();
});