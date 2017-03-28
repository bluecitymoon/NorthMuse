(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('ParameterType', ParameterType);

    ParameterType.$inject = ['$resource'];

    function ParameterType ($resource) {
        var resourceUrl =  'api/parameter-types/:id';

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
