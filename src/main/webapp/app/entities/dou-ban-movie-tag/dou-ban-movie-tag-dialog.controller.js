(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('DouBanMovieTagDialogController', DouBanMovieTagDialogController);

    DouBanMovieTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DouBanMovieTag'];

    function DouBanMovieTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DouBanMovieTag) {
        var vm = this;

        vm.douBanMovieTag = entity;
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
            if (vm.douBanMovieTag.id !== null) {
                DouBanMovieTag.update(vm.douBanMovieTag, onSaveSuccess, onSaveError);
            } else {
                DouBanMovieTag.save(vm.douBanMovieTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('northMuseApp:douBanMovieTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
