dojo.provide("rfs.TransferRow");

dojo.require("dojo.cache");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");

dojo.declare("rfs.TransferRow", [ dijit._Widget, dijit._Templated ], {

    templateString: dojo.cache("rfs", "TransferRow.html")


});