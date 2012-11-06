define(["jquery", "underscore", "underscore.string"], function($, _, _s){
    return {
        initJQueryAjax: function(app){
            var crossDomainRootUrl = app.crossDomainRootUrl();
            $.ajaxPrefilter(function(options, originalOptions, jqXHR){
                if(crossDomainRootUrl && !_s.startsWith(options.url, "http")){
                    options.url = crossDomainRootUrl + options.url;
                }
            });
        }
    };
});