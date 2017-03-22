(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('WebSiteUrl', WebSiteUrl);

    WebSiteUrl.$inject = ['$resource'];

    function WebSiteUrl ($resource) {
        var resourceUrl =  'api/web-site-urls/:id';

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
