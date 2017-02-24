(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteDialogController', WebSiteDialogController);

    WebSiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WebSite'];

    function WebSiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WebSite) {
        var vm = this;

        vm.webSite = entity;
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
            if (vm.webSite.id !== null) {
                WebSite.update(vm.webSite, onSaveSuccess, onSaveError);
            } else {
                WebSite.save(vm.webSite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:webSiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
