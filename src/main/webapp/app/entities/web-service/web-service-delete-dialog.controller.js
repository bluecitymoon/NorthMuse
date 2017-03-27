(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebServiceDeleteController',WebServiceDeleteController);

    WebServiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'WebService'];

    function WebServiceDeleteController($uibModalInstance, entity, WebService) {
        var vm = this;

        vm.webService = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WebService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
