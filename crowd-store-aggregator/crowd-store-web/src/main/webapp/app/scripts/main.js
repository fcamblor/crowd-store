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
        "backbone": {
            deps: ["underscore", "jquery"],
            export: "Backbone"
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
