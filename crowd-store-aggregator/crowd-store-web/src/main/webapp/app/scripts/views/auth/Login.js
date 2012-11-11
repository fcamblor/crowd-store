define(["hbs!templates/auth/login", "backbone", "underscore", "crypto-sha512", "models/Application-renamed"],
    function(tmplContent, Backbone, _, CryptoJS, app){

    return Backbone.View.extend({
        events: {
            "click #authenticateBtn": "authenticate"
        },

        initialize: function(){
            this.model = new Backbone.DeepModel();

            this._modelBinder = new Backbone.ModelBinder();
        },

        close: function(){
            this._modelBinder.unbind();
        },

        render: function(){
            this.$el.html(tmplContent({}));

            this._modelBinder.bind(this.model, this.el, _.extend(Backbone.ModelBinder.createDefaultBindings(this.el, "name"), {
                // Handling password field specifically : we should fill
                // model's hashedPassword field on password field change
                // Note that the model's password (non encrypted) field will not be used during
                // authentication (see authenticate() method)
                password: { selector: "[name=password]", converter: function(direction, value, attName, model){
                    if(direction === "ModelToView"){
                        return value;
                    } else {
                        model.set({ hashedPassword: value === ""?"":CryptoJS.SHA512(value).toString() });
                        return value;
                    }
                }}
            }));

            return this;
        },

        authenticate: function(){
            var $self = this;

            // Resetting potential spring errors
            this.$el.cleanSpringErrors(true);

            // Authenticating...
            var credentials = this.model.toJSON();
            // Deleting useless password field on credentials
            // (only used for user inputs, it is automatically converted into hashedPassword,
            // see converter defined above)
            delete credentials.password;
            $.when(app.login(credentials)).fail(function(){
                // TODO: Beautify this :-)
                alert("Authentication error !");
            });
        }
    });
});