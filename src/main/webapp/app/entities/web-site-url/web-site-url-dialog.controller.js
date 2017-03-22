(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteUrlDialogController', WebSiteUrlDialogController);

    WebSiteUrlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WebSiteUrl', 'WebSite'];

    function WebSiteUrlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WebSiteUrl, WebSite) {
        var vm = this;

        vm.webSiteUrl = entity;
        vm.clear = clear;
        vm.save = save;
        vm.websites = WebSite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.webSiteUrl.id !== null) {
                WebSiteUrl.update(vm.webSiteUrl, onSaveSuccess, onSaveError);
            } else {
                WebSiteUrl.save(vm.webSiteUrl, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:webSiteUrlUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
