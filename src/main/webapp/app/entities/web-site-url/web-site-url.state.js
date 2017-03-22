(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('web-site-url', {
            parent: 'entity',
            url: '/web-site-url?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webSiteUrl.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-site-url/web-site-urls.html',
                    controller: 'WebSiteUrlController',
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
                    $translatePartialLoader.addPart('webSiteUrl');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('web-site-url-detail', {
            parent: 'web-site-url',
            url: '/web-site-url/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.webSiteUrl.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/web-site-url/web-site-url-detail.html',
                    controller: 'WebSiteUrlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('webSiteUrl');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WebSiteUrl', function($stateParams, WebSiteUrl) {
                    return WebSiteUrl.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'web-site-url',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('web-site-url-detail.edit', {
            parent: 'web-site-url-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site-url/web-site-url-dialog.html',
                    controller: 'WebSiteUrlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebSiteUrl', function(WebSiteUrl) {
                            return WebSiteUrl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-site-url.new', {
            parent: 'web-site-url',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site-url/web-site-url-dialog.html',
                    controller: 'WebSiteUrlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rootAddress: null,
                                fullAddress: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('web-site-url', null, { reload: 'web-site-url' });
                }, function() {
                    $state.go('web-site-url');
                });
            }]
        })
        .state('web-site-url.edit', {
            parent: 'web-site-url',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site-url/web-site-url-dialog.html',
                    controller: 'WebSiteUrlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WebSiteUrl', function(WebSiteUrl) {
                            return WebSiteUrl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-site-url', null, { reload: 'web-site-url' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('web-site-url.delete', {
            parent: 'web-site-url',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/web-site-url/web-site-url-delete-dialog.html',
                    controller: 'WebSiteUrlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WebSiteUrl', function(WebSiteUrl) {
                            return WebSiteUrl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('web-site-url', null, { reload: 'web-site-url' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
