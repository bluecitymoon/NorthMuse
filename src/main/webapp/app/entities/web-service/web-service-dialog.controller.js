(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebServiceDialogController', WebServiceDialogController);

    WebServiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WebService'];

    function WebServiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WebService) {
        var vm = this;

        vm.webService = entity;
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
            if (vm.webService.id !== null) {
                WebService.update(vm.webService, onSaveSuccess, onSaveError);
            } else {
                WebService.save(vm.webService, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:webServiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
