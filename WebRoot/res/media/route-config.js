/**
 * 动态加载js文件工具
 */
define([], function () {

    var $controllerProvider,
        $compileProvider;

    function setControllerProvider(value) {
        $controllerProvider = value;
    }

    function setCompileProvider(value) {
        $compileProvider = value;
    }

    function config(option) {

        var defer,
            routeDefinition = {};
        
        routeDefinition.url = option.url;
        routeDefinition.templateUrl = option.templateUrl;
        routeDefinition.controller = option.controllerName;
        routeDefinition.resolve = {
            delay:function ($q, $rootScope) {
                defer = $q.defer();
                var dependencies = [option.controllerName];
                
                require(dependencies, function (controller) {
                	if(option.controllerName){
                		$controllerProvider.register(option.controllerName, controller[option.method]);
                	}
                    defer.resolve();
                })
                return defer.promise;
            }
        }
       
        return routeDefinition;
    }

    return {
        setControllerProvider:setControllerProvider,
        setCompileProvider:setCompileProvider,
        config:config
    }
})
