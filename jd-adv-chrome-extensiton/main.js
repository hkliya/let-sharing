setTimeout(injectAdv, 1000);

function injectAdv() {
  var $slider = $("#focus .slider-main li").first().next();
  console.log($slider);

  $slider.find("a").attr("href", "http://10.2.200.93:1024");
  $slider.find("img").attr("src", "http://ww3.sinaimg.cn/large/61412e43gw1excxlb76erj219812g47g.jpg");

  $slider.prependTo($("#focus .slider-main"));
}
