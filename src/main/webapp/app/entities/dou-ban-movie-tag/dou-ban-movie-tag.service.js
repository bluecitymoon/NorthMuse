(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('DouBanMovieTag', DouBanMovieTag);

    DouBanMovieTag.$inject = ['$resource'];

    function DouBanMovieTag ($resource) {
        var resourceUrl =  'api/dou-ban-movie-tags/:id';

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
