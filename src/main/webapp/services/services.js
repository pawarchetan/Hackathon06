angular.module('sdsApp.services', [])
    .service('getRecordsFromServer',function($http) {
    return {
        fetchRecords: function (formData) {
            return $http({
                method: 'POST',
                url:'http://localhost:8080/insight/databasemetadata/tables',
                //headers : {'Accept' : 'application/json', 'Content-type' : 'application/json'},
                data: formData
              })
            }
        }
    });



