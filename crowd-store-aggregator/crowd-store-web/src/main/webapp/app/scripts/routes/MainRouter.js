define(["jquery", "backbone"], function($, Backbone){

    var dynamicSection = $("#container");

    var MainRouter = Backbone.Router.extend({
        routes: {
            "!/*url": "invokeView"
        },

        // Provide a mapping between :
        // Key : an url
        // Value : A require.js path relative to views/ directory, representing
        //   view which will be loaded when corresponding url is met
        viewByUrl: {
            "auth/login": "auth/Login",
            "stores/:storeToken/availableProducts": "stores/StoreAvailableProducts"
        },

        // Will be calculated in initialize() depending on viewByUrl field
        viewByUrlPattern: null,

        currentView: null,

        initialize: function(){
            MainRouter.__super__.initialize.call(this);

            this.viewByUrlPattern = this.createViewByUrlPattern(this.viewByUrl);
        },

        invokeView: function(url) {
            var viewInfos = this.findViewInfosByUrl(url);
            if(!viewInfos){
                throw "Missing view name declaration in viewByUrl for url : "+url;
            }

            // Requiring view and invoking render() on it
            require(["views/"+viewInfos.viewName], function(ViewClass){
                // Fixing memory leak, by cleaning previous view (and attached event on it)
                if(this.currentView){
                    this.currentView.undelegateEvents();
                    this.currentView.$el.empty();
                }

                // Instantiating view..
                this.currentView = new ViewClass(viewInfos.parameters);
                this.currentView.setElement(dynamicSection);
                // ... then calling render() on it
                this.currentView.render();
            });
        },

        findViewInfosByUrl: function(url) {
            var urlPatterns = this.viewByUrlPattern;
            var matchingViewUrlPattern = _.find(_.keys(urlPatterns), function(routeUrl){
                return urlPatterns[routeUrl].urlPattern.test(url);
            });

            if(matchingViewUrlPattern){
                return {
                    matchingViewUrlPattern: matchingViewUrlPattern,
                    viewName: urlPatterns[matchingViewUrlPattern].view,
                    parameters: this._extractParameters(urlPatterns[matchingViewUrlPattern].urlPattern, url)
                };
            } else {
                return null;
            }
        },

        createViewByUrlPattern: function(viewByUrl){
            var $self = this;
            var viewByUrlPattern = {};
            _.each(_.keys(viewByUrl), function(routeUrl){
                var urlPattern = _.isRegExp(routeUrl)?routeUrl:$self._routeToRegExp(routeUrl);
                viewByUrlPattern[routeUrl] = { urlPattern: urlPattern, view: viewByUrl[routeUrl] };
            });
            return viewByUrlPattern;
        }
    });

    return MainRouter;
});

