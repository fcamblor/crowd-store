define(["backbone", "underscore"],
    function(Backbone, _){

    return Backbone.DeepModel.extend({
        defaults:{

        },

        initialize: function(args){
            Backbone.DeepModel.prototype.initialize.call(this, args);
        }
    });
});