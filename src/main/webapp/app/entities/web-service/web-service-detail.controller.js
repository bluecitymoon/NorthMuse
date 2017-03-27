(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebServiceDetailController', WebServiceDetailController);

    WebServiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WebService'];

    function WebServiceDetailController($scope, $rootScope, $stateParams, previousState, entity, WebService) {
        var vm = this;

        vm.webService = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:webServiceUpdate', function(event, result) {
            vm.webService = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
