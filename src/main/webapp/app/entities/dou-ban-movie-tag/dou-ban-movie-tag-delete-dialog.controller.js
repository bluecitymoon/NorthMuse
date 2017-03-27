(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('DouBanMovieTagDeleteController',DouBanMovieTagDeleteController);

    DouBanMovieTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'DouBanMovieTag'];

    function DouBanMovieTagDeleteController($uibModalInstance, entity, DouBanMovieTag) {
        var vm = this;

        vm.douBanMovieTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DouBanMovieTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
