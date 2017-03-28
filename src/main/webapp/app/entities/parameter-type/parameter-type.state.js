(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parameter-type', {
            parent: 'entity',
            url: '/parameter-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.parameterType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parameter-type/parameter-types.html',
                    controller: 'ParameterTypeController',
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
                    $translatePartialLoader.addPart('parameterType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('parameter-type-detail', {
            parent: 'parameter-type',
            url: '/parameter-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.parameterType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parameter-type/parameter-type-detail.html',
                    controller: 'ParameterTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('parameterType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ParameterType', function($stateParams, ParameterType) {
                    return ParameterType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parameter-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parameter-type-detail.edit', {
            parent: 'parameter-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter-type/parameter-type-dialog.html',
                    controller: 'ParameterTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParameterType', function(ParameterType) {
                            return ParameterType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parameter-type.new', {
            parent: 'parameter-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter-type/parameter-type-dialog.html',
                    controller: 'ParameterTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                identifier: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parameter-type', null, { reload: 'parameter-type' });
                }, function() {
                    $state.go('parameter-type');
                });
            }]
        })
        .state('parameter-type.edit', {
            parent: 'parameter-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter-type/parameter-type-dialog.html',
                    controller: 'ParameterTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParameterType', function(ParameterType) {
                            return ParameterType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parameter-type', null, { reload: 'parameter-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parameter-type.delete', {
            parent: 'parameter-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter-type/parameter-type-delete-dialog.html',
                    controller: 'ParameterTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParameterType', function(ParameterType) {
                            return ParameterType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parameter-type', null, { reload: 'parameter-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
