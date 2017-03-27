(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('web-service', {
            parent: 'entity',
            url: '/web-service?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webService.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-service/web-services.html',
                    controller: 'WebServiceController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('webService');
                    $translatePartialLoader.addPart('wsHttpMethod');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('web-service-detail', {
            parent: 'web-service',
            url: '/web-service/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webService.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-service/web-service-detail.html',
                    controller: 'WebServiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('webService');
                    $translatePartialLoader.addPart('wsHttpMethod');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WebService', function($stateParams, WebService) {
                    return WebService.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'web-service',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('web-service-detail.edit', {
            parent: 'web-service-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-service/web-service-dialog.html',
                    controller: 'WebServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebService', function(WebService) {
                            return WebService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-service.new', {
            parent: 'web-service',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-service/web-service-dialog.html',
                    controller: 'WebServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                url: null,
                                method: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('web-service', null, { reload: 'web-service' });
                }, function() {
                    $state.go('web-service');
                });
            }]
        })
        .state('web-service.edit', {
            parent: 'web-service',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-service/web-service-dialog.html',
                    controller: 'WebServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebService', function(WebService) {
                            return WebService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-service', null, { reload: 'web-service' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-service.delete', {
            parent: 'web-service',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-service/web-service-delete-dialog.html',
                    controller: 'WebServiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WebService', function(WebService) {
                            return WebService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-service', null, { reload: 'web-service' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
