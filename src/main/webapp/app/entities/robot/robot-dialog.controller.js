(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('RobotDialogController', RobotDialogController);

    RobotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Robot', 'WebSiteUrl'];

    function RobotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Robot, WebSiteUrl) {
        var vm = this;

        vm.robot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.websiteurls = WebSiteUrl.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.robot.id !== null) {
                Robot.update(vm.robot, onSaveSuccess, onSaveError);
            } else {
                Robot.save(vm.robot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:robotUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
