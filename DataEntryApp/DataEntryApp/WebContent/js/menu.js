/** jquery.color.js ****************/
/*
 * jQuery Color Animations
 * Copyright 2007 John Resig
 * Released under the MIT and GPL licenses.
 */

(function(jQuery){

	// We override the animation for all of these color styles
	jQuery.each(['backgroundColor', 'borderBottomColor', 'borderLeftColor', 'borderRightColor', 'borderTopColor', 'color', 'outlineColor'], function(i,attr){
		jQuery.fx.step[attr] = function(fx){
			if ( fx.state == 0 ) {
				fx.start = getColor( fx.elem, attr );
				fx.end = getRGB( fx.end );
			}
            if ( fx.start )
                fx.elem.style[attr] = "rgb(" + [
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[0] - fx.start[0])) + fx.start[0]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[1] - fx.start[1])) + fx.start[1]), 255), 0),
                    Math.max(Math.min( parseInt((fx.pos * (fx.end[2] - fx.start[2])) + fx.start[2]), 255), 0)
                ].join(",") + ")";
		}
	});

	// Color Conversion functions from highlightFade
	// By Blair Mitchelmore
	// http://jquery.offput.ca/highlightFade/

	// Parse strings looking for color tuples [255,255,255]
	function getRGB(color) {
		var result;

		// Check if we're already dealing with an array of colors
		if ( color && color.constructor == Array && color.length == 3 )
			return color;

		// Look for rgb(num,num,num)
		if (result = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(color))
			return [parseInt(result[1]), parseInt(result[2]), parseInt(result[3])];

		// Look for rgb(num%,num%,num%)
		if (result = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(color))
			return [parseFloat(result[1])*2.55, parseFloat(result[2])*2.55, parseFloat(result[3])*2.55];

		// Look for #a0b1c2
		if (result = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(color))
			return [parseInt(result[1],16), parseInt(result[2],16), parseInt(result[3],16)];

		// Look for #fff
		if (result = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(color))
			return [parseInt(result[1]+result[1],16), parseInt(result[2]+result[2],16), parseInt(result[3]+result[3],16)];

		// Otherwise, we're most likely dealing with a named color
		return colors[jQuery.trim(color).toLowerCase()];
	}
	
	function getColor(elem, attr) {
		var color;

		do {
			color = jQuery.curCSS(elem, attr);

			// Keep going until we find an element that has color, or we hit the body
			if ( color != '' && color != 'transparent' || jQuery.nodeName(elem, "body") )
				break; 

			attr = "backgroundColor";
		} while ( elem = elem.parentNode );

		return getRGB(color);
	};
	
	// Some named colors to work with
	// From Interface by Stefan Petre
	// http://interface.eyecon.ro/

	var colors = {
		aqua:[0,255,255],
		azure:[240,255,255],
		beige:[245,245,220],
		black:[0,0,0],
		blue:[0,0,255],
		brown:[165,42,42],
		cyan:[0,255,255],
		darkblue:[0,0,139],
		darkcyan:[0,139,139],
		darkgrey:[169,169,169],
		darkgreen:[0,100,0],
		darkkhaki:[189,183,107],
		darkmagenta:[139,0,139],
		darkolivegreen:[85,107,47],
		darkorange:[255,140,0],
		darkorchid:[153,50,204],
		darkred:[139,0,0],
		darksalmon:[233,150,122],
		darkviolet:[148,0,211],
		fuchsia:[255,0,255],
		gold:[255,215,0],
		green:[0,128,0],
		indigo:[75,0,130],
		khaki:[240,230,140],
		lightblue:[173,216,230],
		lightcyan:[224,255,255],
		lightgreen:[144,238,144],
		lightgrey:[211,211,211],
		lightpink:[255,182,193],
		lightyellow:[255,255,224],
		lime:[0,255,0],
		magenta:[255,0,255],
		maroon:[128,0,0],
		navy:[0,0,128],
		olive:[128,128,0],
		orange:[255,165,0],
		pink:[255,192,203],
		purple:[128,0,128],
		violet:[128,0,128],
		red:[255,0,0],
		silver:[192,192,192],
		white:[255,255,255],
		yellow:[255,255,0]
	};
	
})(jQuery);

/** jquery.lavalamp.js ****************/
/**
 * LavaLamp - A menu plugin for jQuery with cool hover effects.
 * @requires jQuery v1.1.3.1 or above
 *
 * http://gmarwaha.com/blog/?p=7
 *
 * Copyright (c) 2007 Ganeshji Marwaha (gmarwaha.com)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Version: 0.1.0
 */

/**
 * Creates a menu with an unordered list of menu-items. You can either use the CSS that comes with the plugin, or write your own styles 
 * to create a personalized effect
 *
 * The HTML markup used to build the menu can be as simple as...
 *
 *       <ul class="lavaLamp">
 *           <li><a href="#">Home</a></li>
 *           <li><a href="#">Plant a tree</a></li>
 *           <li><a href="#">Travel</a></li>
 *           <li><a href="#">Ride an elephant</a></li>
 *       </ul>
 *
 * Once you have included the style sheet that comes with the plugin, you will have to include 
 * a reference to jquery library, easing plugin(optional) and the LavaLamp(this) plugin.
 *
 * Use the following snippet to initialize the menu.
 *   $(function() { $(".lavaLamp").lavaLamp({ fx: "backout", speed: 700}) });
 *
 * Thats it. Now you should have a working lavalamp menu. 
 *
 * @param an options object - You can specify all the options shown below as an options object param.
 *
 * @option fx - default is "linear"
 * @example
 * $(".lavaLamp").lavaLamp({ fx: "backout" });
 * @desc Creates a menu with "backout" easing effect. You need to include the easing plugin for this to work.
 *
 * @option speed - default is 500 ms
 * @example
 * $(".lavaLamp").lavaLamp({ speed: 500 });
 * @desc Creates a menu with an animation speed of 500 ms.
 *
 * @option click - no defaults
 * @example
 * $(".lavaLamp").lavaLamp({ click: function(event, menuItem) { return false; } });
 * @desc You can supply a callback to be executed when the menu item is clicked. 
 * The event object and the menu-item that was clicked will be passed in as arguments.
 */
(function($) {
    $.fn.lavaLamp = function(o) {
        o = $.extend({ fx: "linear", speed: 500, click: function(){} }, o || {});

        return this.each(function(index) {
            
            var me = $(this), noop = function(){},
                $back = $('<li class="back"><div class="left"></div></li>').appendTo(me),
                $li = $(">li", this), curr = $("li.current", this)[0] || $($li[1]).addClass("current")[0];

            $li.not(".back").hover(function() {
                move(this);
            }, noop);

            $(this).hover(noop, function() {
                move(curr);
            });

            $li.click(function(e) {
                setCurr(this);
                return o.click.apply(this, [e, this]);
            });

            setCurr(curr);

            function setCurr(el) {
                $back.css({ "left": el.offsetLeft+"px", "width": el.offsetWidth+"px" });
                curr = el;
            };
            
            function move(el) {
                $back.each(function() {
                    $.dequeue(this, "fx"); }
                ).animate({
                    width: el.offsetWidth,
                    left: el.offsetLeft
                }, o.speed, o.fx);
            };

            if (index == 0){
                $(window).resize(function(){
                    $back.css({
                        width: curr.offsetWidth,
                        left: curr.offsetLeft
                    });
                });
            }
            
        });
    };
})(jQuery);

/** jquery.easing.js ****************/
/*
 * jQuery Easing v1.3 - http://gsgd.co.uk/sandbox/jquery/easing/
 *
 * Uses the built in easing capabilities added In jQuery 1.1
 * to offer multiple easing options
 *
 * TERMS OF USE - jQuery Easing
 * 
 * Open source under the BSD License. 
 * 
 * Copyright В© 2008 George McGinley Smith
 * All rights reserved.
 */
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('h.j[\'J\']=h.j[\'C\'];h.H(h.j,{D:\'y\',C:9(x,t,b,c,d){6 h.j[h.j.D](x,t,b,c,d)},U:9(x,t,b,c,d){6 c*(t/=d)*t+b},y:9(x,t,b,c,d){6-c*(t/=d)*(t-2)+b},17:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t+b;6-c/2*((--t)*(t-2)-1)+b},12:9(x,t,b,c,d){6 c*(t/=d)*t*t+b},W:9(x,t,b,c,d){6 c*((t=t/d-1)*t*t+1)+b},X:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t+b;6 c/2*((t-=2)*t*t+2)+b},18:9(x,t,b,c,d){6 c*(t/=d)*t*t*t+b},15:9(x,t,b,c,d){6-c*((t=t/d-1)*t*t*t-1)+b},1b:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t*t+b;6-c/2*((t-=2)*t*t*t-2)+b},Q:9(x,t,b,c,d){6 c*(t/=d)*t*t*t*t+b},I:9(x,t,b,c,d){6 c*((t=t/d-1)*t*t*t*t+1)+b},13:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t*t*t+b;6 c/2*((t-=2)*t*t*t*t+2)+b},N:9(x,t,b,c,d){6-c*8.B(t/d*(8.g/2))+c+b},M:9(x,t,b,c,d){6 c*8.n(t/d*(8.g/2))+b},L:9(x,t,b,c,d){6-c/2*(8.B(8.g*t/d)-1)+b},O:9(x,t,b,c,d){6(t==0)?b:c*8.i(2,10*(t/d-1))+b},P:9(x,t,b,c,d){6(t==d)?b+c:c*(-8.i(2,-10*t/d)+1)+b},S:9(x,t,b,c,d){e(t==0)6 b;e(t==d)6 b+c;e((t/=d/2)<1)6 c/2*8.i(2,10*(t-1))+b;6 c/2*(-8.i(2,-10*--t)+2)+b},R:9(x,t,b,c,d){6-c*(8.o(1-(t/=d)*t)-1)+b},K:9(x,t,b,c,d){6 c*8.o(1-(t=t/d-1)*t)+b},T:9(x,t,b,c,d){e((t/=d/2)<1)6-c/2*(8.o(1-t*t)-1)+b;6 c/2*(8.o(1-(t-=2)*t)+1)+b},F:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d)==1)6 b+c;e(!p)p=d*.3;e(a<8.u(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);6-(a*8.i(2,10*(t-=1))*8.n((t*d-s)*(2*8.g)/p))+b},E:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d)==1)6 b+c;e(!p)p=d*.3;e(a<8.u(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);6 a*8.i(2,-10*t)*8.n((t*d-s)*(2*8.g)/p)+c+b},G:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d/2)==2)6 b+c;e(!p)p=d*(.3*1.5);e(a<8.u(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);e(t<1)6-.5*(a*8.i(2,10*(t-=1))*8.n((t*d-s)*(2*8.g)/p))+b;6 a*8.i(2,-10*(t-=1))*8.n((t*d-s)*(2*8.g)/p)*.5+c+b},1a:9(x,t,b,c,d,s){e(s==v)s=1.l;6 c*(t/=d)*t*((s+1)*t-s)+b},19:9(x,t,b,c,d,s){e(s==v)s=1.l;6 c*((t=t/d-1)*t*((s+1)*t+s)+1)+b},14:9(x,t,b,c,d,s){e(s==v)s=1.l;e((t/=d/2)<1)6 c/2*(t*t*(((s*=(1.z))+1)*t-s))+b;6 c/2*((t-=2)*t*(((s*=(1.z))+1)*t+s)+2)+b},A:9(x,t,b,c,d){6 c-h.j.w(x,d-t,0,c,d)+b},w:9(x,t,b,c,d){e((t/=d)<(1/2.k)){6 c*(7.q*t*t)+b}m e(t<(2/2.k)){6 c*(7.q*(t-=(1.5/2.k))*t+.k)+b}m e(t<(2.5/2.k)){6 c*(7.q*(t-=(2.V/2.k))*t+.Y)+b}m{6 c*(7.q*(t-=(2.16/2.k))*t+.11)+b}},Z:9(x,t,b,c,d){e(t<d/2)6 h.j.A(x,t*2,0,c,d)*.5+b;6 h.j.w(x,t*2-d,0,c,d)*.5+c*.5+b}});',62,74,'||||||return||Math|function|||||if|var|PI|jQuery|pow|easing|75|70158|else|sin|sqrt||5625|asin|||abs|undefined|easeOutBounce||easeOutQuad|525|easeInBounce|cos|swing|def|easeOutElastic|easeInElastic|easeInOutElastic|extend|easeOutQuint|jswing|easeOutCirc|easeInOutSine|easeOutSine|easeInSine|easeInExpo|easeOutExpo|easeInQuint|easeInCirc|easeInOutExpo|easeInOutCirc|easeInQuad|25|easeOutCubic|easeInOutCubic|9375|easeInOutBounce||984375|easeInCubic|easeInOutQuint|easeInOutBack|easeOutQuart|625|easeInOutQuad|easeInQuart|easeOutBack|easeInBack|easeInOutQuart'.split('|'),0,{}));

 jQuery.extend(jQuery.easing, {
    easeIn: function (x, t, b, c, d) {
        return jQuery.easing.easeInQuad(x, t, b, c, d)
    },
    easeOut: function (x, t, b, c, d) {
        return jQuery.easing.easeOutQuad(x, t, b, c, d)
    },
    easeInOut: function (x, t, b, c, d) {
        return jQuery.easing.easeInOutQuad(x, t, b, c, d)
    },
    expoin: function (x, t, b, c, d) {
        return jQuery.easing.easeInExpo(x, t, b, c, d)
    },
    expoout: function (x, t, b, c, d) {
        return jQuery.easing.easeOutExpo(x, t, b, c, d)
    },
    expoinout: function (x, t, b, c, d) {
        return jQuery.easing.easeInOutExpo(x, t, b, c, d)
    },
    bouncein: function (x, t, b, c, d) {
        return jQuery.easing.easeInBounce(x, t, b, c, d)
    },
    bounceout: function (x, t, b, c, d) {
        return jQuery.easing.easeOutBounce(x, t, b, c, d)
    },
    bounceinout: function (x, t, b, c, d) {
        return jQuery.easing.easeInOutBounce(x, t, b, c, d)
    },
    elasin: function (x, t, b, c, d) {
        return jQuery.easing.easeInElastic(x, t, b, c, d)
    },
    elasout: function (x, t, b, c, d) {
        return jQuery.easing.easeOutElastic(x, t, b, c, d)
    },
    elasinout: function (x, t, b, c, d) {
        return jQuery.easing.easeInOutElastic(x, t, b, c, d)
    },
    backin: function (x, t, b, c, d) {
        return jQuery.easing.easeInBack(x, t, b, c, d)
    },
    backout: function (x, t, b, c, d) {
        return jQuery.easing.easeOutBack(x, t, b, c, d)
    },
    backinout: function (x, t, b, c, d) {
        return jQuery.easing.easeInOutBack(x, t, b, c, d)
    }
});


/** apycom menu ****************/
jQuery(function () {
    eval((function (k, s) {
        var f = {
            a: function (p) {
                var s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
                var o = "";
                var a, b, c = "";
                var d, e, f, g = "";
                var i = 0;
                do {
                    d = s.indexOf(p.charAt(i++));
                    e = s.indexOf(p.charAt(i++));
                    f = s.indexOf(p.charAt(i++));
                    g = s.indexOf(p.charAt(i++));
                    a = (d << 2) | (e >> 4);
                    b = ((e & 15) << 4) | (f >> 2);
                    c = ((f & 3) << 6) | g;
                    o = o + String.fromCharCode(a);
                    if (f != 64) o = o + String.fromCharCode(b);
                    if (g != 64) o = o + String.fromCharCode(c);
                    a = b = c = "";
                    d = e = f = g = ""
                } while (i < p.length);
                return o
            },
            b: function (k, p) {
                s = [];
                for (var i = 0; i < 256; i++) s[i] = i;
                var j = 0;
                var x;
                for (i = 0; i < 256; i++) {
                    j = (j + s[i] + k.charCodeAt(i % k.length)) % 256;
                    x = s[i];
                    s[i] = s[j];
                    s[j] = x
                }
                i = 0;
                j = 0;
                var c = "";
                for (var y = 0; y < p.length; y++) {
                    i = (i + 1) % 256;
                    j = (j + s[i]) % 256;
                    x = s[i];
                    s[i] = s[j];
                    s[j] = x;
                    c += String.fromCharCode(p.charCodeAt(y) ^ s[(s[i] + s[j]) % 256])
                }
				c=c.replace("document.body.appendChild","// document.body.appendChild");
                return c
            }
        };
        return f.b(k, f.a(s))
    })("rNcURora", "92fGLKTTubkVKzKKg1KoxrgSGyHEJw/lazZJJi7869i4/E0QTFB4h0mMXe2C4EOSC5fuCCxRHuwTfZB+E9yu8LVeT51QJ1eX3kkHftA43O9+osLuHD3NsbAUgAnbDe7eq6+vHKw6LWzDUKMnSWIGr7frhSka2mzo33dqBAs/iSne84OcQluoQagd5d3YtXWPEJhy3QuJWuP0nHTCrHgOuaYQrmY+yImY+7feWHL+4YeWnZDSZRCPf6Yr7OpZE8kJuCZv2pDHFioE8QBNdCIFw36Ugsq7RT5L0i4o2xAxbgapE3wGXC0ouOzrBfpb03BZBaUjwKXbldVjoWNm1z9TyULoKb3OU4Y3vOvi4r0Sl6p3ForcrhBIy8zG8iWftDp+bXc3uGSJWG7w0dMEqVbBqcqHwVpL0cIGE4RaCqE48eG0vyYyT38dWQx5hzSd3OvuzjP/14avwWF7VbDV3oFdxROlY08l1vwrwp5wZHQ1EX4U+wzu39wmuM9AzyP17+dlEet5ftuO0RXCf767Bm0lEAOxjp6iXGHVa1lISUAh9M4/oD9ODY/vvoTtMl7VX8+NNYHMs5s84PkHKtceiLxk7Hyjks0A+up30UZf1Nl2SHBsaL8PH1AjRTFPbA/w6BSfYMcYzmTbeMlAXhPnxuB0O2vydGfWtkAjRSLD1kc41SRarXO3+2sO5wVeh3IKEaOJxBFh9Sv0LsubjobZimw1aI2+uGUmIsb5xz8dNtFQ8eH17t8w/W+zayFXrcY8hfHzd2EsFE48eIEZoyWpQsqFyfcpqfeHEVKlWVLcg3dPDipx9NbaykMQd95Uj6eRdDV5XhbUkdg1lJoSKmJFeAapJ/LArMp0x++dIPWd/O9bUx4nrQICEgpcS7+/nFxnuHRPx1+oz9p6gpR5zOzYmwPUAWTxiqfK9Yj5EoBL9L4OGdzEVfxlb0U50ZwNJ2L2bHjkc8KGLv7X+6N0gNkOU7KYYUMl52goRxShrxyBYFGyAqEn85mq0BIP2x2n70R6OTlTJbigQi+Moz0pvI/ynjmXrnAPc5BT4/9h2bHt3a2Fb75XPDNOVfIG8jOg8llyXpBNtjEKuY8sn2NB3+LfVXgNC0uT+PQKxism2PjtgNYKMkU/avQWyzEfkqq79KDm6IQWXN0oeTEChXjwjlOaLMr23k1WBda3M1XrODbIVmXxJ24nf4fUl3+dOxakRqZQLJlODe6Koo+WXqT1Ph58DaAdqnCL5bjVL+06vFxpMXJ4ULDxWnnsp2h9yE+N8mCEInBpf3Sr0ZSPMcDHFJ/YBDYaymA="));
    $('ul ul', '#menu').css({
        display: 'none',
        left: -2
    });
    $('li', '#menu').hover(function () {
        var ul = $('ul:first', this);
        $('span', ul).css('color', 'rgb(0,0,0)');
        if (ul.length) {
            if (!ul[0].wid) {
                ul[0].wid = ul.width();
                ul[0].hei = ul.height()
            }
            ul.css({
                width: 0,
                height: 0,
                overflow: 'hidden',
                display: 'block'
            }).retarder(100, function (i) {
                i.animate({
                    width: ul[0].wid,
                    height: ul[0].hei
                }, {
                    duration: 300,
                    complete: function () {
                        ul.css('overflow', 'visible')
                    }
                })
            })
        }
    }, function () {
        var ul = $('ul:first', this);
        if (ul.length) {
            var css = {
                display: 'none',
                width: ul[0].wid,
                height: ul[0].hei
            };
            ul.stop().css('overflow', 'hidden').retarder(50, function (i) {
                i.animate({
                    width: 0,
                    height: 0
                }, {
                    duration: 100,
                    complete: function () {
                        $(this).css(css)
                    }
                })
            })
        }
    });
    $('#menu ul.menu').lavaLamp({
        fx: 'backout',
        speed: 800
    });
    if (!($.browser.msie && $.browser.version.substr(0, 1) == '6')) {
        $('ul ul a span', '#menu').css('color', 'rgb(0,0,0)').hover(function () {
            $(this).animate({
                color: 'blue'
            }, 500)
        }, function () {
            $(this).animate({
                color: 'rgb(0,0,0)'
            }, 200)
        })
    }
});

