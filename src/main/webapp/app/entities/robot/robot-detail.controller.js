(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('RobotDetailController', RobotDetailController);

    RobotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Robot', 'WebSiteUrl'];

    function RobotDetailController($scope, $rootScope, $stateParams, previousState, entity, Robot, WebSiteUrl) {
        var vm = this;

        vm.robot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:robotUpdate', function(event, result) {
            vm.robot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
