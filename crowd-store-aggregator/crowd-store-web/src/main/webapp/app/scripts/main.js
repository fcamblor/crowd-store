require.config({
    baseUrl: "scripts",
    paths: {
        "components": "../components",
        "backbone": "../components/backbone/backbone",
        "jquery": "../components/jquery/jquery",
        "underscore": "../components/underscore/underscore",
        "bootstrap": "vendor/bootstrap/bootstrap"
    },
    shim: {
        // "default" backbone.js files are not AMD-compatible
        // => We must not consider it as proper backbone dependency, this is why we add here
        // shim config which provide window.Backbone as module dependency
        "backbone": {
            deps: ["underscore", "jquery"],
            // Note : This is strange that exports:"Backbone" is not enough
            // for strange reasons, we have to confirm it with init function
            // because otherwise, window.Backbone won't be provided as module dependency
            exports: "Backbone",
            init: function(){ return this.Backbone; }
        },
        "underscore": {
            deps: [],
            exports: "_"
        },
        // Most of twitter boostrap components need jquery loaded
        "components/bootstrap/js/bootstrap-affix": ["jquery"],
        "components/bootstrap/js/bootstrap-alert": ["jquery"],
        "components/bootstrap/js/bootstrap-button": ["jquery"],
        "components/bootstrap/js/bootstrap-carousel": ["jquery"],
        "components/bootstrap/js/bootstrap-collapse": ["jquery"],
        "components/bootstrap/js/bootstrap-dropdown": ["jquery"],
        "components/bootstrap/js/bootstrap-modal": ["jquery"],
        "components/bootstrap/js/bootstrap-tooltip": ["jquery"],
        // popover component needs tooltip component fully loaded
        "components/bootstrap/js/bootstrap-popover": ["jquery", "components/bootstrap/js/bootstrap-tooltip"],
        "components/bootstrap/js/bootstrap-scrollspy": ["jquery"],
        "components/bootstrap/js/bootstrap-tab": ["jquery"],
        "components/bootstrap/js/bootstrap-transition": ["jquery"],
        "components/bootstrap/js/bootstrap-typeahead": ["jquery"]
    }
});

require(["jquery", "underscore", "backbone", "models/application", "bootstrap"], function($, _, Backbone, Application){

    $(document).ready(function(){
        window.app = new Application();
        window.app.start();
    });
});
