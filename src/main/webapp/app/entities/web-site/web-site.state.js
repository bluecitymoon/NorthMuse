(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('web-site', {
            parent: 'entity',
            url: '/web-site?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webSite.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-site/web-sites.html',
                    controller: 'WebSiteController',
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
                    $translatePartialLoader.addPart('webSite');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('web-site-detail', {
            parent: 'web-site',
            url: '/web-site/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webSite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-site/web-site-detail.html',
                    controller: 'WebSiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('webSite');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WebSite', function($stateParams, WebSite) {
                    return WebSite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'web-site',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('web-site-detail.edit', {
            parent: 'web-site-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site/web-site-dialog.html',
                    controller: 'WebSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebSite', function(WebSite) {
                            return WebSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-site.new', {
            parent: 'web-site',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site/web-site-dialog.html',
                    controller: 'WebSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                rootUrl: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('web-site', null, { reload: 'web-site' });
                }, function() {
                    $state.go('web-site');
                });
            }]
        })
        .state('web-site.edit', {
            parent: 'web-site',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site/web-site-dialog.html',
                    controller: 'WebSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebSite', function(WebSite) {
                            return WebSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-site', null, { reload: 'web-site' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-site.delete', {
            parent: 'web-site',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site/web-site-delete-dialog.html',
                    controller: 'WebSiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WebSite', function(WebSite) {
                            return WebSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-site', null, { reload: 'web-site' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
