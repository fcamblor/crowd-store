define(["jquery", "underscore", "underscore.string", "helpers/js-utils"], function($, _, _s, JsUtils){
    return {
        initJQueryAjax: function(app){
            // Handling cross domain urls, particularly between yeoman server
            // and jee server
            var crossDomainRootUrl = app.crossDomainRootUrl();
            if(crossDomainRootUrl){
                $.ajaxPrefilter(function(options, originalOptions, jqXHR){
                    if(!_s.startsWith(options.url, "http")){
                        options.url = crossDomainRootUrl + options.url;
                    }
                });
            }

            // This prevent jquery to add square brackets [] at the end of param name when they have multiple values,
            // which is breaking spring binding.
            $.ajaxSettings.traditional = true;

            /**
             * Overriding default behaviour for ajax errors : looking at jquery XMLHttpRequest's status
             * and, if it is the special 412 (Precondition failed) status _and_ response is a JSON representation,
             * then call $.displaySpringErrors()
             */
            $(document).ajaxError(function(event, jqXHR, settings, exception){
                if(jqXHR.status === 412 && JsUtils.isJSON(jqXHR.responseText)){
                    var springBindingResults = $.parseJSON(jqXHR.responseText);
                    $.displaySpringErrors(springBindingResults);
                }
            });
        }
    };
});