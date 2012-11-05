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

        currentView: null,

        invokeView: function(url) {
            var viewName = this.viewByUrl[url];
            if(!viewName){
                throw "Missing view name declaration in viewByUrl for url : "+url;
            }

            // Requiring view and invoking render() on it
            require(["views/"+viewName], function(ViewClass){
                // Fixing memory leak, by cleaning previous view (and attached event on it)
                if(this.currentView){
                    this.currentView.remove();
                }

                // Instantiating view..
                this.currentView = new ViewClass();
                this.currentView.setElement(dynamicSection);
                // ... then calling render() on it
                this.currentView.render();
            });
        }
    });
});

