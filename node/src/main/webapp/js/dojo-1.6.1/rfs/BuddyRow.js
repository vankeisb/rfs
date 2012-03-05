dojo.provide("rfs.BuddyRow");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");

dojo.declare("rfs.BuddyRow", [ dijit._Widget, dijit._Templated ], {

    templateString: dojo.cache("rfs", "BuddyRow.html"),

    _cssClass: '',

    buildRendering: function() {
        var b = this.buddy;
        console.log(b);
        if (b.online) {
            this._cssClass = b.auth ? "online" : "authFailed";
        } else {
            this._cssClass = "offline";
        }
        this.inherited(arguments);
    }


});