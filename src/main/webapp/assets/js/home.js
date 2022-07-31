$(document).ready(function(){
    $("body").scroll(function(){
        console.log('a');
    });
});
$('<div class="quantity-nav"><div class="quantity-button quantity-up">+</div><div class="quantity-button quantity-down">-</div></div>').insertAfter('.quantity input');
$('.quantity').each(function() {
    var spinner = $(this),
        input = spinner.find('input[type="number"]'),
        btnUp = spinner.find('.quantity-up'),
        btnDown = spinner.find('.quantity-down'),
        min = input.attr('min'),
        max = input.attr('max');

    btnUp.click(function() {
        var oldValue = parseFloat(input.val());
        if (oldValue >= max) {
        var newVal = oldValue;
        } else {
        var newVal = oldValue + 1;
        }
        spinner.find("input").val(newVal);
        spinner.find("input").trigger("change");
        btnDown.click(function() {
        var oldValue = parseFloat(input.val());
        if (oldValue <= min) {
        var newVal = oldValue;
        } else {
        var newVal = oldValue - 1;
        }
        spinner.find("input").val(newVal);
        spinner.find("input").trigger("change");
    });
});
});
$('#icon_close_cart').on('click',function() {
    $('.cart').removeClass('show').addClass('hide');
})
$('.add--cart').on('click',function(){
    $('.cart').removeClass('hide').addClass('show');
})
$('#show_cart').on('click',function(){
    $('.cart').removeClass('hide').addClass('show');
})
$('.content--product--top h4').on('click',function(){
    $('.show--info--product').removeClass('hide').addClass('show');
})
$('#btnClose').on('click',function() {
    $('.show--info--product').removeClass('show').addClass('hide');
})