define(["backbone"], function(){

    return Backbone.Router.extend({
        routes: {
            "!/*url": "invokeView"
        },

        viewByUrl: {
            "foo/testing": "TestingView"
        },

        invokeView: function(url) {
            var viewName = this.viewByUrl[url];
            if(!viewName){
                throw "Missing view name declaration in viewByUrl for url : "+url;
            }

            // Requiring view and invoking render() on it
            require(["views/"+viewName], function(ViewClass){
                var viewInstance = new ViewClass();
                viewInstance.render();
            });
        }
    });
});

