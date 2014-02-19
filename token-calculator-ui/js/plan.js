function Plan() {
  this.ranges = [];
  this.nodes_count = 0;
  this.total_weight = 0;
  this.factors = [];
  this.backgrounds = [];

  this.backgrounds_tpl = [
    "radial-gradient(35% 25%, 50% width, #fff, #73bff1, #539fe1)",
    "radial-gradient(35% 25%, 50% width, #fff, #a47e5a, #744e2a)",
    "radial-gradient(35% 25%, 50% width, #fff, #81e439, #81e439)",
    "radial-gradient(35% 25%, 50% width, #fff, #d8dae6, #a8aab6)",
    "radial-gradient(35% 25%, 50% width, #fff, #ED978E, #ED978E)",
    "radial-gradient(35% 25%, 50% width, #fff, #febf01, #febf01)"
  ];
}

Plan.prototype.addRange = function(range) {
  this.ranges.push(range);
  this.nodes_count += range.nodes_count;
  this.total_weight += range.nodes_count * range.weight;

  this.factors = [];
  var bi = 0;
  this.backgrounds = [];
  for (var i in this.ranges) {
    var r = this.ranges[i];

    for (var n = 0; n<r.nodes_count; n++) {
      this.factors.push(r.weight/this.total_weight)

      this.backgrounds.push(this.backgrounds_tpl[bi])
    }

    // background index
    bi +=1;
    if (bi > this.backgrounds_tpl.length-1) {
      bi = 0;
    }
  }

  // Shift clockwise
  this.factors.push(this.factors.shift());
  this.backgrounds.push(this.backgrounds.shift());

  // View
  $('<div></div>').html(range.nodes_count + ' nodes x ' + range.weight + " GB").appendTo($('.plan'));
  $('.capacity').html(this.nodes_count + " nodes, "+ numeral(this.total_weight).format('0,0') + " GB");
};

Plan.prototype.calculate_positions = function () {
  var positions = [];
  var accumulated_factor = 0;

  for (var i = 0; i<this.nodes_count; i++) {
    accumulated_factor += this.factors[i];
    var position = 360 * accumulated_factor;
    positions.push(position);
  }
  return positions;
};

Plan.prototype.calculate_tokens = function () {
  var max_token = BigInteger(2).pow(BigInteger(127));
  var tokens = [];

  var accumulated_factor = 0;
  for (var i = 0; i<this.nodes_count; i++) {
    var token = max_token.multiply(Math.round(accumulated_factor*Math.pow(10,25))).divide(Math.pow(10, 25));
    tokens.push(token);
    accumulated_factor += this.factors[i];
  }

  return tokens;
};

Plan.prototype.calculate_tokens_simple = function () {
  var max_token = BigInteger(2).pow(BigInteger(127));
  var tokens = [];

  for (var i = 0; i<this.nodes_count; i++) {
    var token = max_token.multiply(i).divide(this.nodes_count);
    tokens.push(token);
  }
  return tokens;
};

Plan.prototype.calculate_positions_simple = function () {
  var positions = [];

  for (var i = 0; i<this.nodes_count; i++) {
    var position = i*360/this.nodes_count;
    positions.push(position);
  }
  return positions;
};

Plan.prototype.clear = function () {
  this.ranges = [];
  this.nodes_count = 0;
  this.total_weight = 0;
  this.factors = [];
  $('#tokens').val("");
  $(".plan").empty();
  $('.capacity').empty();
};