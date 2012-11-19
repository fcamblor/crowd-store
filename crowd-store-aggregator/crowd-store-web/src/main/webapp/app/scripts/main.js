require.config({
    baseUrl: "scripts",
    paths: {
        "components": "../components",
        "backbone": "../components/backbone/backbone",
        "bbModelBinder": "../components/Backbone.ModelBinder/Backbone.ModelBinder",
        "bbCollectionBinder": "../components/Backbone.ModelBinder/Backbone.CollectionBinder",
        "bbDeepModel": "../components/backbone-deep-model/src/deep-model",
        "jquery": "../components/jquery/jquery",
        "underscore": "../components/underscore/underscore",
        "underscore.string": "../components/underscore.string/lib/underscore.string",
        "bootstrap": "vendor/bootstrap/bootstrap",
        "json2": "../components/require-handlebars-plugin/hbs/json2",
        "handlebars": "../components/handlebars/handlebars-1.0.0-rc.1",
        "i18nprecompile": "../components/require-handlebars-plugin/hbs/i18nprecompile",
        "hbs": "../components/require-handlebars-plugin/hbs",
        "crypto-sha512": "../components/crypto-sha512/index"
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
        "bbModelBinder": {
            deps: ["backbone"],
            exports: "Backbone.ModelBinder"
        },
        "bbCollectionBinder": {
            deps: ["bbModelBinder"],
            exports: "Backbone.CollectionBinder"
        },
        "bbDeepModel": {
            deps: [],
            exports: "Backbone.DeepModel"
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
        "components/bootstrap/js/bootstrap-typeahead": ["jquery"],
        "json2": {
            deps: [],
            exports: "JSON"
        },
        "handlebars": {
            deps: [],
            exports: "Handlebars"
        },
        "crypto-sha512": {
            deps: [],
            exports: "CryptoJS"
        }
    },
    /*
    // hbs is a requirejs plugin, so we should load it as soon as possible...
    deps: [
        "hbs"
    ],
    */
    // hbs particular configuration properties
    hbs: {
        disableI18n: true // Support for i18n is useless for the moment...
    }
});

// hbs is a requirejs plugin, so we should load it as soon as possible in order to be
// available in further requirements
require([
    "hbs"
], function(){

    // First loading every common dependencies needed to start executing some javascript
    require([
        "jquery", "helpers/handlebars-helpers", "jquery-plugins/spring-validationerrors",
        "backbone", "bbModelBinder", "bbCollectionBinder", "bbDeepModel",
        "bootstrap"
    ], function($){

        // Now loading Application objects and view in order to start Application
        require([
            "models/Application", "views/ApplicationView"
        ], function(app, appView){

            $(document).ready(function(){
                app.start();
                appView.start();
            });
        });

    });

});
