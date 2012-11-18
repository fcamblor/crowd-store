define(["hbs!templates/menus/navmenus", "hbs!templates/menus/navsubmenus", "backbone", "underscore", "models/Application"],
    function(tmplNavMenus, tmplNavSubmenus, Backbone, _, App){

    var ApplicationViewClass = Backbone.View.extend({
        el: "body",

        events: {
        },

        initialize: function(){
            this._menusModelBinder = null;
            this._modelBinders = {};

            // Should be a projection of navigation menus looking like this :
            // [
            //    {
            //      label: "root menu label",
            //      [href: "an optional link",]
            //      [submenu: Backbone.Collection() of { label: "submenu label", href: "an url" }]
            //    }
            // ]
            this.menus = new Backbone.Collection([]);

            App.bind("change:currentUser.*", this.updateUserMenus, this);
        },

        // Should never be called...
        close: function(){
            var modelBinders = this._modelBinders;
            _.each(_.keys(modelBinders), function(key){
                modelBinders[key].unbind();
            });
        },

        _registerModelBinder: function(id, modelBinder){
            this._modelBinders[id] = modelBinder;
        },

        _unregisterModelBinder: function(id){
            this._modelBinders[id].unbind();
            delete this._modelBinders[id];
        },

        updateUserMenus: function(app, currentUser){
            if(!currentUser){
                this.menus.reset();
            } else {
                var $self = this;
                /*
                this.menus.add({
                    label: "Stores",
                    submenus: new Backbone.Collection([])
                });
                */
                //var storesSubmenus = this.menus.at(this.menus.length-1).get("submenus");
                _.each(_.keys(currentUser.storesAuthorizations), function(storeName){
                    $self.menus.add({ label: storeName, href: "#!/stores/"+storeName+"/availableProducts" });
                    //storesSubmenus.add({ label: storeName, href: "#!/stores/"+storeName+"/availableProducts" });
                });

                this.menus.add({
                    label: "Debts",
                    href: "#debts"
                });
                this.menus.add({
                    label: "Preferences",
                    submenus: new Backbone.Collection([])
                });
            }
        },

        start: function(){
            var $self = this;

            // Declaring root menus collection model binder
            this._menusModelBinder = new Backbone.CollectionBinder(
                new Backbone.CollectionBinder.ElManagerFactory(tmplNavMenus({}), {
                    href: { selector: "[data-name=hyperlink]", elAttribute:"href" },
                    label: { selector: "[data-name=label]" }
                })
            );
            this._menusModelBinder.on("elCreated", function(model, el){
                // On submenu creation, we should declare an additional
                // modelbinder (for submenus)
                var modelSubmenus = model.get("submenus");
                if(modelSubmenus){
                    var submenuModelBinder = new Backbone.CollectionBinder(
                        new Backbone.CollectionBinder.ElManagerFactory(tmplNavSubmenus({}), {
                            href: { selector: "[data-name=submenu-label]", elAttribute:"href" },
                            label: { selector: "[data-name=submenu-label]" }
                        })
                    );
                    $self._registerModelBinder(model.cid, submenuModelBinder);
                    submenuModelBinder.bind(modelSubmenus, el.find(".dropdown-menu"));
                }
            });
            this._menusModelBinder.on("elRemoved", function(model, el){
                $self._unregisterModelBinder(model.cid);
            });
            this._menusModelBinder.bind(this.menus, this.$el.find("#main-menu-left"));
            // Binding root menus to this.menus
            this._registerModelBinder(this.menus.cid, this._menusModelBinder);
        }
    });

    return new ApplicationViewClass();
});