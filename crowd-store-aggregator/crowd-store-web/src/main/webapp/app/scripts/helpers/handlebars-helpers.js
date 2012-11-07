define(["handlebars", "underscore",
    "hbs!templates/handlebars-helpers/field",
    "hbs!templates/handlebars-helpers/input"
], function(Handlebars, _, fieldTpl, inputTpl){

        Handlebars.registerHelper("html-field", function(context, options){
            return fieldTpl(_.extend(options.hash, { content: new Handlebars.SafeString(options.fn(options.hash)) }));
        });
        Handlebars.registerHelper("html-input", function(context, options){
            return inputTpl(options.hash);
        });
});