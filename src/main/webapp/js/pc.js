
		$(document).ready(function(){

			$(function(){            //ul/li的折叠效果
				 $(".left ul li").click(function(){
					 var i=0;
					 var j=0;
					 for(i;i<$(".left ul li").length;i++){
						$(".left ul li").eq(i).removeClass('current')
					 }
					 for(j;j<$(".menu_tab").length;j++){
						$(".menu_tab").eq(j).removeClass('active');
					 }
				 		//找menu对应的tab
						 var val=($(this).attr('class'));
						 $(this).addClass("current");
				 		var menu_value=(val.substring(val.length-1));
				 		$(".content .menu"+menu_value).addClass("active");
				 });			 	
			});
			
			$(function(){   // 导航 >
				 $(".left ul li").click(function(){
				 	$(".right .A1").empty().text($(this).text());
			});
		});

		});



