(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('UrlParameter', UrlParameter);

    UrlParameter.$inject = ['$resource'];

    function UrlParameter ($resource) {
        var resourceUrl =  'api/url-parameters/:id';

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
