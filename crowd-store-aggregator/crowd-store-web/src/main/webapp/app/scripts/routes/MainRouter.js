define(["jquery", "backbone"], function($, Backbone){

    var dynamicSection = $("#container");

    return Backbone.Router.extend({
        routes: {
            "!/*url": "invokeView"
        },

        // Provide a mapping between :
        // Key : an url
        // Value : A require.js path relative to views/ directory, representing
        //   view which will be loaded when corresponding url is met
        viewByUrl: {
            "auth/login": "auth/Login"
        },

        invokeView: function(url) {
            var viewName = this.viewByUrl[url];
            if(!viewName){
                throw "Missing view name declaration in viewByUrl for url : "+url;
            }

            // Requiring view and invoking render() on it
            require(["views/"+viewName], function(ViewClass){
                // Instantiating view..
                var viewInstance = new ViewClass();

                // ... then calling render() on it and including rendering result
                // into dynamicSection
                dynamicSection.html(viewInstance.render());
            });
        }
    });
});

