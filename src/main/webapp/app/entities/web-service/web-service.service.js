(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('WebService', WebService);

    WebService.$inject = ['$resource'];

    function WebService ($resource) {
        var resourceUrl =  'api/web-services/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
