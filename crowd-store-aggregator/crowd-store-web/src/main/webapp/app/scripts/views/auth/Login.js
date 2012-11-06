define(["hbs!templates/auth/login", "backbone"], function(tmplContent, Backbone){
    return Backbone.View.extend({
        events: {
            "click #authenticateBtn": "authenticate"
        },

        initialize: function(){
            this.model = new Backbone.DeepModel();
            this.model.set({ credentials: { login: "foo@bar.com" } });

            this._modelBinder = new Backbone.ModelBinder();
        },

        close: function(){
            this._modelBinder.unbind();
        },

        render: function(){
            this.$el.html(tmplContent({}));
            this._modelBinder.bind(this.model, this.el);
            return this;
        },

        authenticate: function(){
            window.app.login(this.model.toJSON().credentials);
        }
    });
});