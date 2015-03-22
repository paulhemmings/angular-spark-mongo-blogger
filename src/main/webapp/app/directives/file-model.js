/*
 * http://jsfiddle.net/JeJenny/ZG9re/
 * http://uncorkedstudios.com/blog/multipartformdata-file-upload-with-angularjs
 */

angular
    .module('MainApplicationModule')
    .directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);