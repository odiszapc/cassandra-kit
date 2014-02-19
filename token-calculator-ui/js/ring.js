

function Ring(options) {

  this.node_radius_ratio = 1/70;
  this.node_scale = 1;

  var options_default = {
    node: {
      stroke: "1px #999",
      background: "radial-gradient(35% 25%, 50% width, #fff, #73bff1, #539fe1)",
    }
 	};

 	this.options = jQuery.extend(true, options_default, options);
  this.nodes = [];

  this.canvas = oCanvas.create({
    canvas: "#canvas",
    background: this.options.background
  });

  //this.node_radius = this.canvas.width * this.node_radius_ratio;

  this.nodeProto = this.canvas.display.ellipse({
    //radius: this.node_radius,
    fill: this.options.node.background,
    stroke: this.options.node.stroke

  });

  this.ring = this.canvas.display.ellipse({
    x: this.canvas.width / 2, y: this.canvas.height / 2,
    radius: this.canvas.width * 0.4,
    stroke: "2px #666"
  }).add();
}

Ring.prototype.clear = function () {
  for (var i in this.nodes) {
    this.ring.removeChild(this.nodes[i]);
    this.nodes.remove[i];
  }
};

Ring.prototype.addNode = function (node) {

  var node_obj = this.nodeProto.clone({
    origin: {
      x: this.ring.origin.x * -1,
      y: this.ring.radius
    },
    rotation: node.options.position,
    radius: this.canvas.width * this.node_radius_ratio * this.node_scale,
    fill: node.options.view.background,
    x: 0,
    y: 0
  });
  this.nodes.push(node_obj)

  this.ring.addChild(node_obj);

};





function Node(options) {
  var options_default = {
    token: null,
    view: {
      stroke: "1px #bbb",
      background: "radial-gradient(35% 25%, 50% width, #fff, #87bcea, #539fe1, #0a77d5)",
      node_radius_ratio: 1/60
    }

 	};

 	this.options = jQuery.extend(true, options_default, options);

}







