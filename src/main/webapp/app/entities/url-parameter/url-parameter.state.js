(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('url-parameter', {
            parent: 'entity',
            url: '/url-parameter?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.urlParameter.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/url-parameter/url-parameters.html',
                    controller: 'UrlParameterController',
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
                    $translatePartialLoader.addPart('urlParameter');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('url-parameter-detail', {
            parent: 'url-parameter',
            url: '/url-parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.urlParameter.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/url-parameter/url-parameter-detail.html',
                    controller: 'UrlParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('urlParameter');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UrlParameter', function($stateParams, UrlParameter) {
                    return UrlParameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'url-parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('url-parameter-detail.edit', {
            parent: 'url-parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/url-parameter/url-parameter-dialog.html',
                    controller: 'UrlParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UrlParameter', function(UrlParameter) {
                            return UrlParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('url-parameter.new', {
            parent: 'url-parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/url-parameter/url-parameter-dialog.html',
                    controller: 'UrlParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                paramKey: null,
                                paramValue: null,
                                defaultValue: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('url-parameter', null, { reload: 'url-parameter' });
                }, function() {
                    $state.go('url-parameter');
                });
            }]
        })
        .state('url-parameter.edit', {
            parent: 'url-parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/url-parameter/url-parameter-dialog.html',
                    controller: 'UrlParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UrlParameter', function(UrlParameter) {
                            return UrlParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('url-parameter', null, { reload: 'url-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('url-parameter.delete', {
            parent: 'url-parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/url-parameter/url-parameter-delete-dialog.html',
                    controller: 'UrlParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UrlParameter', function(UrlParameter) {
                            return UrlParameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('url-parameter', null, { reload: 'url-parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
