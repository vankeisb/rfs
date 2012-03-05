dojo.provide("rfs.Buddies");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");
dojo.require("rfs.BuddyRow");

dojo.declare("rfs.Buddies", dijit._Widget, {

    baseUrl: "",

    _buddies: [],

    buildRendering: function() {
        this._cli = new woko.rpc.Client(this.baseUrl);
        this.inherited(arguments);
        dojo.addClass(this.domNode, "buddies");
        this.refresh();
    },

    refresh: function() {
        // grab list of transfers from server
        this._cli.invokeFacet("buddies", {
            onSuccess: dojo.hitch(this, function(resp) {
                var buddies = resp.items;
                // remove all previous transfers
                dojo.forEach(this._buddies, dojo.hitch(this, function(t) {
                    t.destroy();
                }));
                dojo.empty(this.domNode);
                this._buddies = [];
                // ad new transfers
                dojo.forEach(buddies, dojo.hitch(this, function(b) {
                    var buddyRow = new rfs.BuddyRow({
                        buddy: b
                    });
                    buddyRow.startup();
                    this.domNode.appendChild(buddyRow.domNode);
                }));
                //setTimeout(dojo.hitch(this, this.refresh), 5000);
            }),
            onError: function(err) {
                // TODO
                console.log(err);
            }
        });
    }



});
