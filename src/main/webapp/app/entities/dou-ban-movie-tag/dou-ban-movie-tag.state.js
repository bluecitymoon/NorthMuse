(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dou-ban-movie-tag', {
            parent: 'entity',
            url: '/dou-ban-movie-tag?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.douBanMovieTag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tags.html',
                    controller: 'DouBanMovieTagController',
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
                    $translatePartialLoader.addPart('douBanMovieTag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dou-ban-movie-tag-detail', {
            parent: 'dou-ban-movie-tag',
            url: '/dou-ban-movie-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'northMuseApp.douBanMovieTag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tag-detail.html',
                    controller: 'DouBanMovieTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('douBanMovieTag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DouBanMovieTag', function($stateParams, DouBanMovieTag) {
                    return DouBanMovieTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dou-ban-movie-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dou-ban-movie-tag-detail.edit', {
            parent: 'dou-ban-movie-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tag-dialog.html',
                    controller: 'DouBanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DouBanMovieTag', function(DouBanMovieTag) {
                            return DouBanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dou-ban-movie-tag.new', {
            parent: 'dou-ban-movie-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tag-dialog.html',
                    controller: 'DouBanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tag: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dou-ban-movie-tag', null, { reload: 'dou-ban-movie-tag' });
                }, function() {
                    $state.go('dou-ban-movie-tag');
                });
            }]
        })
        .state('dou-ban-movie-tag.edit', {
            parent: 'dou-ban-movie-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tag-dialog.html',
                    controller: 'DouBanMovieTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DouBanMovieTag', function(DouBanMovieTag) {
                            return DouBanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dou-ban-movie-tag', null, { reload: 'dou-ban-movie-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dou-ban-movie-tag.delete', {
            parent: 'dou-ban-movie-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dou-ban-movie-tag/dou-ban-movie-tag-delete-dialog.html',
                    controller: 'DouBanMovieTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DouBanMovieTag', function(DouBanMovieTag) {
                            return DouBanMovieTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dou-ban-movie-tag', null, { reload: 'dou-ban-movie-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
