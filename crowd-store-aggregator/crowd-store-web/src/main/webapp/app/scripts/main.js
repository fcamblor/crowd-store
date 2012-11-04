require(["jquery", "underscore", "backbone", "models/application", "bootstrap"], function($, _, Backbone, Application){

    $(document).ready(function(){
        window.app = new Application();
        window.app.start();
    });
});
