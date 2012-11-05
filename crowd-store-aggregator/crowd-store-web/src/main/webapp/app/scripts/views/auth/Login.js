define(["hbs!templates/auth/login"], function(tmplContent){
    return Backbone.View.extend({
        id: "loginForm",
        events: {
            "click #authenticateBtn": "authenticate"
        },

        render: function(){
            return tmplContent({});
        },

        authenticate: function(){
            alert("Submit action detected !");
        }
    });
});