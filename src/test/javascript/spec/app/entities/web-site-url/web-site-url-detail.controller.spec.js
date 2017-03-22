'use strict';

describe('Controller Tests', function() {

    describe('WebSiteUrl Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWebSiteUrl, MockWebSite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWebSiteUrl = jasmine.createSpy('MockWebSiteUrl');
            MockWebSite = jasmine.createSpy('MockWebSite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WebSiteUrl': MockWebSiteUrl,
                'WebSite': MockWebSite
            };
            createController = function() {
                $injector.get('$controller')("WebSiteUrlDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'northMuseApp:webSiteUrlUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
