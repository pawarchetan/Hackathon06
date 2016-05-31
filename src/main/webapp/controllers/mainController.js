var mainApp = angular.module("sdsApp.mainController", ['ngRoute']);

mainApp.controller('mainController',['$scope','getRecordsFromServer', function($scope, getRecordsFromServer) {

    var currentIndex = 0;
    var todayDate = new Date();
    $scope.stockList = [];
    $scope.searchForm = {};
    $scope.visibilityMode = "view";


    $scope.searchCriteria = function() {

        if($scope.searchForm.serverUrl) {
            console.log($scope.searchForm.serverUrl);
        } else {
            console.log(null);
        }

        if($scope.searchForm.username) {
            console.log($scope.searchForm.username);
        } else {
            console.log(null);
        }

        if($scope.searchForm.password) {
            console.log($scope.searchForm.password);
        } else {
            console.log(null);
        }

        if($scope.searchForm.databaseType) {
            console.log($scope.searchForm.databaseType);
        } else {
            console.log(null);
        }

        if($scope.searchForm.percentage) {
            console.log($scope.searchForm.percentage);
        } else {
            $scope.searchForm.percentage = null;
            console.log(null);
        }

        if($scope.searchForm.ssn) {
            console.log($scope.searchForm.ssn);
        } else {
            $scope.searchForm.ssn = false;
            console.log(false);
        }

        if($scope.searchForm.cc) {
            console.log($scope.searchForm.cc);
        } else {
            $scope.searchForm.cc = false;
            console.log(false);
        }

        if($scope.searchForm.email) {
            console.log($scope.searchForm.email);
        } else {
            $scope.searchForm.email = false;
            console.log(false);
        }

        console.log($scope.searchForm);

        var  params = {
            "connectionURL": $scope.searchForm.serverUrl,
            "userName": $scope.searchForm.username,
            "password": $scope.searchForm.password
        }

        console.log(params);
        if(params.password == undefined || params.password == null){
            params.password = "";
            
        }
            getRecordsFromServer.fetchRecords(params)
             .success(function (data) {
                $scope.responseObject = data;
                $scope.tableNames = Object.keys(data);
                   console.log('response returned');
                  //angular.forEach(data, function (value) {
                  //    $scope.records.push(value);
                  //});
             });
        

    }
    $scope.sampleSearch = function(){
        $scope.sampleObject = {
            t1: {
                c1: "true",
                //c2:false
            },
            t2: {
                c1: "false",
                //c2: true
            },
            t3:{
                c1: "true",
                //c2: false
            }
        }
        
    }
    $scope.status = function(status){
        return status == 'Negative' ? 'warning': 'ok';
        
    }


    $scope.loadMoreRecords = function () {
        // Mocking stock values
        // In an real application, data would be retrieved from an external system

        var chunkSize = 50;
        var stock;
        var i = 0;
        while (i < chunkSize) {
            currentIndex++;
            var newDate = new Date();
            newDate.setDate(todayDate.getDate() - currentIndex);
            if (newDate.getDay() >= 1 && newDate.getDay() <= 5) {
                stock = {
                    dateValue: newDate,
                    price: 20.0 + Math.random() * 10
                };
                $scope.stockList.push(stock);
                i++;
            }
        }
    };

    $scope.loadMoreRecords();

}]);