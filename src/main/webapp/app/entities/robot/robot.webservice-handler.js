(function() {
    'use strict';
    angular
        .module('northMuseApp')
        .factory('RobotHandler', RobotHandler);

    RobotHandler.$inject = ['$http'];

    function RobotHandler ($http) {

        function send(data, webservice) {

            switch (webservice.method) {
                case 'GET':
                    return $http(
                        {
                            method: 'GET',
                            url: webservice.url
                        });
                case 'POST':

                    return $http(
                        {
                            method: 'POST',
                            url: webservice.url,
                            data: data
                        }
                    );
            }
        }

        return {
            send: send
        };
    }
})();
