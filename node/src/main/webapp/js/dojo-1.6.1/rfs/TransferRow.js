dojo.provide("rfs.TransferRow");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");

dojo.declare("rfs.TransferRow", [ dijit._Widget, dijit._Templated ], {

    templateString: dojo.cache("rfs", "TransferRow.html"),

    _cssDirection: "",
    _cssStatus: "",

    buildRendering: function() {
        console.log(this.transfer.download);
        this._cssDirection = this.transfer.download ? "download" : "upload";
        if (this.transfer.error) {
            this._cssStatus = "error";
        } else {
            if (this.transfer.startedOn) {
                if (this.transfer.finishedOn) {
                    this._cssStatus = "complete";
                } else {
                    this._cssStatus = "inProgress";
                }
            } else {
                this._cssStatus = "notStarted";
            }
        }
        this.inherited(arguments);

    }


});