define([
    "hbs!templates/stores/availableProducts",
    "hbs!templates/products/listProduct",
    "backbone", "underscore",
    "models/stores/StoreAvailableProductCollection"
],
    function(tmplContent, tmplListProduct, Backbone, _, StoreAvailableProductCollection){

    var StoreAvailableProducts = Backbone.View.extend({
        events: {
        },

        initialize: function(parameters){
            StoreAvailableProducts.__super__.initialize.call(this, parameters);

            var storeToken = parameters[0];
            this.model = new StoreAvailableProductCollection({ storeToken: storeToken });

            this._modelBinder = new Backbone.CollectionBinder(
                new Backbone.CollectionBinder.ElManagerFactory(tmplListProduct({}), {
                    "id": {
                        selector: "[data-name=product-link]",
                        elAttribute: "href",
                        converter: function(type, val){ return "#!/stores/"+storeToken+"/products/"+val; }
                    },
                    "product.displayName": {
                        selector: "[data-name=displayName]"
                    }
                })
            );
        },

        close: function(){
            this._modelBinder.unbind();
        },

        render: function(){
            var $self = this;
            this.$el.html(tmplContent({}));
            this._modelBinder.bind(this.model, this.$el.find("#products"));

            $.when(this.model.fetch()).done(function(){
                // Nothing to do... binding make the deal automatically :-)
            });

            return this;
        }
    });

    return StoreAvailableProducts;
});