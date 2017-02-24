(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('WebSite', WebSite);

    WebSite.$inject = ['$resource'];

    function WebSite ($resource) {
        var resourceUrl =  'api/web-sites/:id';

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
            'update': { method:'PUT' },
            'analysisWebSite': {url: 'api/web-sites/analysis/:id', method: 'GET'}
        });
    }
})();
