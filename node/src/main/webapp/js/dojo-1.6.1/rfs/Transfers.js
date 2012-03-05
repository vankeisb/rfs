dojo.provide("rfs.Transfers");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");
dojo.require("rfs.TransferRow");

dojo.declare("rfs.Transfers", dijit._Widget, {

    baseUrl: "",

    _cli: null,
    _transfers: [],

    buildRendering: function() {
        this._cli = new woko.rpc.Client(this.baseUrl);
        this.inherited(arguments);
        this.refresh();
    },

    refresh: function() {
        // grab list of transfers from server
        this._cli.invokeFacet("transfers", {
            onSuccess: dojo.hitch(this, function(resp) {
                var transfers = resp.items;
                // remove all previous transfers
                dojo.forEach(this._transfers, dojo.hitch(this, function(t) {
                    t.destroy();
                }));
                dojo.empty(this.domNode);
                this._transfers = [];
                // ad new transfers
                dojo.forEach(transfers, dojo.hitch(this, function(t) {
                    var transferRow = new rfs.TransferRow({
                        transfer: t
                    });
                    transferRow.startup();
                    this.domNode.appendChild(transferRow.domNode);
                }));
                setTimeout(dojo.hitch(this, this.refresh), 5000);
            }),
            onError: function(err) {
                // TODO
                console.log(err);
            }
        });
    }

});
