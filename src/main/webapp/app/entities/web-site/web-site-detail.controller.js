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

        vm.data = {};
        $scope.entries = [];
        vm.analysisWebSite = function () {

            WebSite.analysisWebSite({id: vm.webSite.id}).$promise.then(success, fail);

            function success(data) {

                vm.data = data;
                $scope.entries = data.entries;
                filterEntries();
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
            page: true,
            picture: false
        };

        $scope.checkResults = [];

        $scope.$watchCollection('extensionFilter', function () {

            $scope.checkResults = [];
            angular.forEach($scope.extensionFilter, function (value, key) {
                if (value) {
                    $scope.checkResults.push(key);
                }
            });
            filterEntries();
        });

        function filterEntries() {

            $scope.entries = [];
            angular.forEach(vm.data.entries, function (entry) {

                var requestType = entry.requestType;
                if (requestType) {

                    var containsExtension = $scope.extensionFilter[requestType.toLowerCase()];

                    if (containsExtension == true) {

                        $scope.entries.push(entry);
                    }

                } else {

                    //just show if no request type
                    $scope.entries.push(entry);
                }

            });

            console.debug($scope.entries);
        }

        var unsubscribe = $rootScope.$on('northMuseApp:webSiteUpdate', function(event, result) {
            vm.webSite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
