define(["backbone", "underscore", "models/stores/StoreAvailableProduct"],
    function(Backbone, _, StoreAvailableProduct){

    return Backbone.Collection.extend({
        model: StoreAvailableProduct,
        url: function(){ return "/stores/"+this.storeToken+"/availableProducts"; },

        initialize: function(args){
            Backbone.DeepModel.prototype.initialize.call(this, args);

            this.storeToken = args.storeToken;
        }
    });
});