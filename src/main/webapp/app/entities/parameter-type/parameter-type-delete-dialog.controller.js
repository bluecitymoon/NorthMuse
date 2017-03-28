(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('ParameterTypeDeleteController',ParameterTypeDeleteController);

    ParameterTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParameterType'];

    function ParameterTypeDeleteController($uibModalInstance, entity, ParameterType) {
        var vm = this;

        vm.parameterType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParameterType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
