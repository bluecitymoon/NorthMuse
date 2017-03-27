(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('DouBanMovieTagDetailController', DouBanMovieTagDetailController);

    DouBanMovieTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DouBanMovieTag'];

    function DouBanMovieTagDetailController($scope, $rootScope, $stateParams, previousState, entity, DouBanMovieTag) {
        var vm = this;

        vm.douBanMovieTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('northMuseApp:douBanMovieTagUpdate', function(event, result) {
            vm.douBanMovieTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
