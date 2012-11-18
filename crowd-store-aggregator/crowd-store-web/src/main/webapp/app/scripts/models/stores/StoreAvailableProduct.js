define(["backbone", "underscore"],
    function(Backbone, _){

    var StoreAvailableProduct = Backbone.DeepModel.extend({
        defaults:{

        },

        initialize: function(args){
            StoreAvailableProduct.__super__.initialize.call(this, args);
        }
    });

    return StoreAvailableProduct;
});