(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('UrlParameterDialogController', UrlParameterDialogController);

    UrlParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UrlParameter', 'WebSiteUrl', 'ParameterType'];

    function UrlParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UrlParameter, WebSiteUrl, ParameterType) {
        var vm = this;

        vm.urlParameter = entity;
        vm.clear = clear;
        vm.save = save;
        vm.websiteurls = WebSiteUrl.query();
        vm.parametertypes = ParameterType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.urlParameter.id !== null) {
                UrlParameter.update(vm.urlParameter, onSaveSuccess, onSaveError);
            } else {
                UrlParameter.save(vm.urlParameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:urlParameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
