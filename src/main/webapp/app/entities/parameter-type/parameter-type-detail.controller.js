(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('ParameterTypeDetailController', ParameterTypeDetailController);

    ParameterTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParameterType'];

    function ParameterTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ParameterType) {
        var vm = this;

        vm.parameterType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:parameterTypeUpdate', function(event, result) {
            vm.parameterType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
