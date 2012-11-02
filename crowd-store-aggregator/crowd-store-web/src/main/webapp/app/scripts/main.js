require(["jquery", "underscore", "backbone", "bootstrap"], function($, _, Backbone){
    window.webapp = {
      Models: {},
      Collections: {},
      Views: {},
      Routers: {},
      init: function() {
        console.log('Hello from Backbone!');
      }
    };

    $(document).ready(function(){
      webapp.init();
    });
});
