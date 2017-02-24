(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteDeleteController',WebSiteDeleteController);

    WebSiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'WebSite'];

    function WebSiteDeleteController($uibModalInstance, entity, WebSite) {
        var vm = this;

        vm.webSite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WebSite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
