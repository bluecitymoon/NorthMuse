/**
 * Created by Jerry on 2017/3/28.
 */

(function() {
    'use strict';

    angular
        .module('northMuseApp')
        .config(function (hljsServiceProvider) {
            hljsServiceProvider.setOptions({
                // replace tab with 4 spaces
                tabReplace: '    '
            });
        });
})();

