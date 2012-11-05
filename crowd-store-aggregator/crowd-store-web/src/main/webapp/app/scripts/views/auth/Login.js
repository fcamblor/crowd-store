define(["hbs!templates/auth/login"], function(tmplContent){
    return Backbone.View.extend({
        events: {
            "click #authenticateBtn": "authenticate"
        },

        render: function(){
            this.$el.html(tmplContent({}));
            return this;
        },

        authenticate: function(){
            alert("Submit action detected !");
        }
    });
});