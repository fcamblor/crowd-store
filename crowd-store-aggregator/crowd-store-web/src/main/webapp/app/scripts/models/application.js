define(["backbone", "underscore", "routes/MainRouter", "helpers/ajax"], function(Backbone, _, MainRouter, Ajax){

    return Backbone.Model.extend({
        defaults: {
            router: null,
            currentUser: {},
            crossDomainRootUrl: (window.location.port==="3501")?"http://localhost:8080":null
        },

        initialize: function(attributes, options){
            Ajax.initJQueryAjax(this);
            Backbone.Model.prototype.initialize.call(this, attributes, options);

            this.bind("change:currentUser", this.currentUserUpdated, this);
        },

        start: function(){
            this.set({ router: new MainRouter() });
            // Initializing Backbone history, and eventually routing on homepage
            // if current url fragment is not resolved to a view
            var currentUrlMatchesAView = Backbone.history.start();
            if(!currentUrlMatchesAView){
                this.routeTo("!/auth/login");
            }
        },

        currentUserUpdated: function(){
            console.log("User updated !");
        },

        login: function(credentials){
            var $self = this;
            $.when($.ajax({
                    "url": "/auth/authenticate",
                    "type": "POST",
                    "contentType": "application/json",
                    "data": JSON.stringify(credentials),
                    "dataType": "json"
                })
            ).then(function(authenticatedUser){
                $self.set({ currentUser: authenticatedUser });
            });
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
        currentUser: function(){ return this.get("currentUser"); },
        crossDomainRootUrl: function(){ return this.get("crossDomainRootUrl"); }
    });
});

