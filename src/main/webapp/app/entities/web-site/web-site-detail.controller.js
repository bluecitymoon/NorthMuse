(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteDetailController', WebSiteDetailController);

    WebSiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WebSite'];

    function WebSiteDetailController($scope, $rootScope, $stateParams, previousState, entity, WebSite) {
        var vm = this;

        vm.webSite = entity;
        vm.previousState = previousState.name;

        vm.log = {};
        vm.analysisWebSite = function () {

            WebSite.analysisWebSite({id: vm.webSite.id}).$promise.then(success, fail);

            function success(data) {

                vm.log = data;
            }

            function fail() {

                //TODO
            }

        };

        var unsubscribe = $rootScope.$on('northMuseApp:webSiteUpdate', function(event, result) {
            vm.webSite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
