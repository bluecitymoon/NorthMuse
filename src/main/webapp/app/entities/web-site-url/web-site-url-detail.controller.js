(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteUrlDetailController', WebSiteUrlDetailController);

    WebSiteUrlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WebSiteUrl', 'WebSite'];

    function WebSiteUrlDetailController($scope, $rootScope, $stateParams, previousState, entity, WebSiteUrl, WebSite) {
        var vm = this;

        vm.webSiteUrl = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:webSiteUrlUpdate', function(event, result) {
            vm.webSiteUrl = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
