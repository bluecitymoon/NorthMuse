(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('UrlParameterDetailController', UrlParameterDetailController);

    UrlParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UrlParameter', 'WebSiteUrl', 'ParameterType'];

    function UrlParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, UrlParameter, WebSiteUrl, ParameterType) {
        var vm = this;

        vm.urlParameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:urlParameterUpdate', function(event, result) {
            vm.urlParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
