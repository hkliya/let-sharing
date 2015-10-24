var $btn = $('#pfhlkd_buycar');
var $container = $btn.parent();

var $btnTry = $btn.clone();
$btnTry.addClass('btn-try');
$btnTry.text('加载中...');
$btnTry.prependTo($container);

var $qrcode = $("<img class='qrcode' />");
$qrcode.load(function () {
  $btnTry.text('立即试用');
});

$btnTry.hover(function () {
  $qrcode.toggle();
});

$qrcode.prependTo($container);
$qrcode.hide();
resetQRCode();

injectImportHistoryButton();

$(".pp_prop_attr li").click(function () {
  resetQRCode();
});

function getColor() {
  return $(".pp_prop_attr a.cur span").text();
}

function resetQRCode() {
  $qrcode.attr("src", "http://qr.liantu.com/api.php?el=l&w=400&m=10&text=" + text());
}

function injectImportHistoryButton() {
  var $btnInject = $("<button id='btnImportHistory'>导入到购物助手</button>");
  $btnInject.click(function () {
    importHistory();
  });
  $('#evalu01').before($btnInject);
}

function importHistory() {
  var products = parseProducts();
  uploadToServer(products);
}

function parseProducts() {
  var $items = $('.pro-info .p-item');
  return $.map($items, function (item, index) {
    return {
      name: $.trim($(item).find('.p-name a').text()),
      url: $(item).find('.p-name a').attr('href'),
      imageURL: $(item).find('.p-img img').attr('src'),
      bought_at: $.trim($(item).find('.p-tiem').text()).match(/\d{4}-\d{2}-\d{2}/)
    };
  });
}

function uploadToServer(products) {
  alert('导入成功！');
  alert(JSON.stringify(products));
}

function text() {
  var data = {
    "imageURL": getImageURL(),
    "productName": encodeURIComponent(getProductName()),
    "color": encodeURIComponent(getColor())
  };
  return JSON.stringify(data);
}

function getProductName() {
  return $.trim($('.pp_prop_fn').text());
}

function getImageURL() {
  return $.trim($("#pfhlkd_smallImage").attr("src"));
}
