define(["backbone", "underscore", "models/stores/StoreAvailableProduct"],
    function(Backbone, _, StoreAvailableProduct){

    var StoreAvailableProductCollection = Backbone.Collection.extend({
        model: StoreAvailableProduct,
        url: function(){ return "/stores/"+this.storeToken+"/availableProducts"; },

        initialize: function(args){
            StoreAvailableProductCollection.__super__.initialize.call(this, args);

            this.storeToken = args.storeToken;
        }
    });

    return StoreAvailableProductCollection;
});