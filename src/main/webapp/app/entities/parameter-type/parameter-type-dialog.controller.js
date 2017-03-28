(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('ParameterTypeDialogController', ParameterTypeDialogController);

    ParameterTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParameterType'];

    function ParameterTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParameterType) {
        var vm = this;

        vm.parameterType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.parameterType.id !== null) {
                ParameterType.update(vm.parameterType, onSaveSuccess, onSaveError);
            } else {
                ParameterType.save(vm.parameterType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:parameterTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
