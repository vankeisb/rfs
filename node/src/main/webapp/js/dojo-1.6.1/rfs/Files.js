dojo.provide("rfs.Files");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");

dojo.declare("rfs.Files", dijit._Widget, {

    baseUrl: "",

    _cli: null,
    _files: [],

    onRefresh: null,

    buildRendering: function() {
        this._cli = new woko.rpc.Client(this.baseUrl);
        this.inherited(arguments);
        dojo.addClass(this.domNode, "files");
        this.refresh();
    },

    refresh: function() {
        // grab list of transfers from server
        this._cli.invokeFacet("files", {
            onSuccess: dojo.hitch(this, function(resp) {
                var files = resp.items;
                // remove all previous transfers
                dojo.forEach(this._files, dojo.hitch(this, function(t) {
                    t.destroy();
                }));
                dojo.empty(this.domNode);
                this._files = [];
                // ad new transfers
                if (files) {
                    var container = dojo.create("ul");
                    dojo.forEach(files, dojo.hitch(this, function(t) {
                        var li = dojo.create("li", null, container);
                        dojo.create("a", {innerHTML: t.path, href:this.baseUrl + "/download/File" + t.path}, li);
                    }));
                    this.domNode.appendChild(container);
                }
                if (this.onRefresh) {
                    this.onRefresh.apply(this, [files]);
                }
                setTimeout(dojo.hitch(this, this.refresh), 5000);
            }),
            onError: function(err) {
                // TODO
                console.log(err);
            }
        });
    }

});
