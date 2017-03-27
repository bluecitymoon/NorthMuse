(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('Robot', Robot);

    Robot.$inject = ['$resource', 'DateUtils'];

    function Robot ($resource, DateUtils) {
        var resourceUrl =  'api/robots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'start': {
                method: 'GET',
                url: 'api/robots/start/:id',
                params: { id : '@id'}
            }
        });
    }
})();
