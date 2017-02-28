(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .controller('WebSiteDetailController', WebSiteDetailController);

    WebSiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WebSite'];

    function WebSiteDetailController($scope, $rootScope, $stateParams, previousState, entity, WebSite) {
        var vm = this;

        vm.webSite = entity;
        vm.previousState = previousState.name;
        vm.types = ['JS', 'HTML', 'JSON', 'PICTURE', 'PAGE'];

        vm.log = {};
        vm.analysisWebSite = function () {

            WebSite.analysisWebSite({id: vm.webSite.id}).$promise.then(success, fail);

            function success(data) {

                vm.log = data;
            }

            function fail() {

                //TODO
            }

        };

        vm.toggleDetail = function (l) {
            l.showDetail = !l.showDetail;
        };

        $scope.extensionFilter = {
            js: false,
            css: false,
            html: true,
            json: true,
            page: true
        };

        $scope.checkResults = [];

        $scope.$watchCollection('extensionFilter', function () {
            $scope.checkResults = [];
            angular.forEach($scope.extensionFilter, function (value, key) {
                if (value) {
                    $scope.checkResults.push(key);
                }
            });
            
        });

        var unsubscribe = $rootScope.$on('northMuseApp:webSiteUpdate', function(event, result) {
            vm.webSite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
