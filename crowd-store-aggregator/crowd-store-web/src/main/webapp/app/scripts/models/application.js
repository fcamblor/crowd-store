define(["backbone", "underscore", "routes/MainRouter"], function(Backbone, _, MainRouter){

    return Backbone.Model.extend({
        defaults: {
            router: null,
            currentUser: {}
        },

        initialize: function(attributes, options){
            Backbone.Model.prototype.initialize.call(this, attributes, options);

            this.bind("change:currentUser", this.currentUserUpdated, this);
        },

        start: function(){
            this.set({ router: new MainRouter() });
            // Initializing Backbone history, and eventually routing on homepage
            // if current url fragment is not resolved to a view
            var currentUrlMatchesAView = Backbone.history.start();
            if(!currentUrlMatchesAView){
                this.routeTo("!/foo/testing");
            }
        },

        currentUserUpdated: function(){

        },

        /**
         * Will call navigateTo() and call target route in Backbone (thus, triggering
         * view change)
         * You should use this method in general, instead of directly calling navigateTo()
         */
        routeTo: function(fragment){
            this.navigateTo(fragment, {trigger: true});
        },

        /**
         * Will perform a page refresh to fragment parameter
         */
        refreshPageTo: function(fragment){
            this.routeTo(fragment);
            // Routing .. then reloading the page
            document.location.reload(true);
        },

        navigateTo: function(fragment, options){
            this.get("router").navigate(fragment, options);
        },

        // Aliases
        currentUser: function(){ return this.get("currentUser"); }
    });
});

