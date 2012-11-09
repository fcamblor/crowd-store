define(["hbs!templates/menus/navmenus", "hbs!templates/menus/navsubmenus", "backbone", "underscore", "models/application"],
    function(tmplNavMenus, tmplNavSubmenus, Backbone, _, app){

    var ApplicationViewClass = Backbone.View.extend({
        el: "body",

        events: {
        },

        initialize: function(){
            this._menusModelBinder = null;
            this._modelBinders = {};
            this.menus = new Backbone.Collection([]);
        },

        // Should never be called...
        close: function(){
            var modelBinders = this._modelBinders;
            _.each(_.keys(modelBinders), function(key){
                modelBinders[key].unbind();
            });
        },

        start: function(){
            var $self = this;
            this._menusModelBinder = new Backbone.CollectionBinder(
                new Backbone.CollectionBinder.ElManagerFactory(tmplNavMenus({}), {
                    href: { selector: "[data-name=hyperlink]", elAttribute:"href" },
                    label: { selector: "[data-name=label]" }
                })
            );
            this._modelBinders[this.menus.cid] = this._menusModelBinder;

            this._menusModelBinder.bind(this.menus, this.$el.find("#main-menu-left"));
            this._menusModelBinder.on("elCreated", function(model, el){
                var submenuModelBinder = new Backbone.CollectionBinder(
                    new Backbone.CollectionBinder.ElManagerFactory(tmplNavSubmenus({}), {
                        href: { selector: "[data-name=submenu-label]", elAttribute:"href" },
                        label: { selector: "[data-name=submenu-label]" }
                    })
                );
                $self._modelBinders[model.cid] = submenuModelBinder;
                submenuModelBinder.bind(model.get("submenus"), el.find(".dropdown-menu"));
            });
            this._menusModelBinder.on("elRemoved", function(model, el){
                // TODO : REMOVE $self._modelBinders[model.cid] !
            });

            var menu1 = { label: "4SH canettes", submenus: new Backbone.Collection([]) };
            this.menus.add(menu1);
            menu1.submenus.add({ label: "Sous libellé 1", href: "#foo" });

            var menu2 = { label: "4SH Soccer5", href: "#bar", submenus: new Backbone.Collection([]) };
            this.menus.add(menu2);
        }
    });

    return new ApplicationViewClass();
});