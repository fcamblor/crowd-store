define(["hbs!templates/auth/login"], function(tmplContent){
    return Backbone.View.extend({
        render: function(){
            return tmplContent({});
        }
    });
});