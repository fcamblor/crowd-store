define(["hbs!templates/menus/navmenus", "hbs!templates/menus/navsubmenus", "backbone", "underscore", "models/application"],
    function(tmplNavMenus, tmplNavSubmenus, Backbone, _, app){

    var ApplicationViewClass = Backbone.View.extend({
        el: "body",

        events: {
        },

        initialize: function(){
            this.menus = new Backbone.Collection([]);
        },

        // Should never be called...
        close: function(){
        },

        start: function(){

            var menu1 = { label: "4SH canettes", submenus: new Backbone.Collection([]) };
            this.menus.add(menu1);
            menu1.submenus.add({ label: "Sous libellé 1", href: "#foo" });

            var menu2 = { label: "4SH Soccer5", href: "#bar", submenus: new Backbone.Collection([]) };
            this.menus.add(menu2);
        }
    });

    return new ApplicationViewClass();
});