(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteUrlDeleteController',WebSiteUrlDeleteController);

    WebSiteUrlDeleteController.$inject = ['$uibModalInstance', 'entity', 'WebSiteUrl'];

    function WebSiteUrlDeleteController($uibModalInstance, entity, WebSiteUrl) {
        var vm = this;

        vm.webSiteUrl = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WebSiteUrl.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
