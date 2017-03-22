'use strict';

describe('Controller Tests', function() {

    describe('UrlParameter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUrlParameter, MockWebSiteUrl;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUrlParameter = jasmine.createSpy('MockUrlParameter');
            MockWebSiteUrl = jasmine.createSpy('MockWebSiteUrl');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UrlParameter': MockUrlParameter,
                'WebSiteUrl': MockWebSiteUrl
            };
            createController = function() {
                $injector.get('$controller')("UrlParameterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'northMuseApp:urlParameterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
