//exo1
jquery(document).ready(function(){
 $(".test").hide();
});
//exo02
jquery(document).ready(function(){
    $("p").click(function(){
     $(this).hide();
    });
});

//exo03
jquery(document).ready(function(){
    $("p").on("click", function() {
        $(this).hide();
      });
});
//exo04
jquery(document).ready(function(){
    $("button").click(function() {
        $("p").show();
      });
});

//exo05
jquery(document).ready(function(){
    $("div").fadeTo("slow", 0.2);
});

//exo06
jquery(document).ready(function(){
    $("div").animate({
        height: "500px"
      });
});

//exo07
$("p").addClass("important");

//exo08
$("p").css("background-color", "red");
//exo09
$("div").css({
    "height": "500px",
    "width": "500px"
  });
//exo10
$("span").parent();
//exo11
$("div").children();






