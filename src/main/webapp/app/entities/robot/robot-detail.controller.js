(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('RobotDetailController', RobotDetailController);

    RobotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Robot', 'WebSiteUrl', 'RobotHandler'];

    function RobotDetailController($scope, $rootScope, $stateParams, previousState, entity, Robot, WebSiteUrl, RobotHandler) {
        var vm = this;

        vm.robot = entity;
        vm.previousState = previousState.name;
        vm.startRobot = startRobot;

        var unsubscribe = $rootScope.$on('northMuseApp:robotUpdate', function(event, result) {
            vm.robot = result;
        });

        function startRobot() {

            vm.cmdMessage = ["Robot " + vm.robot.name + " started to working ........"];

            Robot.start({id: vm.robot.id}).$promise.then(function (data) {

                vm.cmdMessage.push("Handle target successfully 👌 👌 👌 👌 👌 with response: ⏬");

                vm.cmdMessage.push(JSON.stringify(data));

                var webService = vm.robot.webService;
                if (webService) {

                    vm.cmdMessage.push("Found web service handler, starting to handle data ....");
                    RobotHandler.send(data, webService).then(function (data) {

                        vm.cmdMessage.push("sent successfully");
                        vm.cmdMessage.push(JSON.stringify(data));

                    }, function (error) {
                        vm.cmdMessage.push("sent failed");
                        vm.cmdMessage.push(error);
                    })

                }

            }, function (error) {

                vm.cmdMessage.push("Handle target failed ❗️ ❗️ ❗️ ❗️ ❗️ ❗️ with response: ⏬");

                vm.cmdMessage.push(error);
            })
        }
        $scope.$on('$destroy', unsubscribe);
    }
})();
