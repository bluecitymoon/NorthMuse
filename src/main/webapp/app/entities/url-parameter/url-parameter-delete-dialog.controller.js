(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('UrlParameterDeleteController',UrlParameterDeleteController);

    UrlParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'UrlParameter'];

    function UrlParameterDeleteController($uibModalInstance, entity, UrlParameter) {
        var vm = this;

        vm.urlParameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UrlParameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
