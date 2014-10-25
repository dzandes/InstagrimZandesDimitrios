/**
 * Document   : scroll.js
 * Created on : Oct 15, 2014
 * Author     : Zandes Dimitrios
 * 
 */

    $(document).ready(function() {
    	
            $('#wrapper').hover(
            function() {
                 
               $('#carousel').trigger( 'pause' );
               $('#thumbnails').parent().animate({top: 280});
               
            }, function() {
    
               $('#carousel').trigger( 'play' );
               $('#thumbnails').parent().animate({top: 375});
    
             }
            );
     
            $('#carousel').carouFredSel({
            	
               scroll: {
                         
            	   fx: 'crossfade',
                   onBefore: function( data ) {
                           
                	   $('#thumbnails').trigger( 'slideTo', [ $('#thumbnails img[alt='+ data.items.visible.attr( 'alt' ) +']'), -2 ] );
              
                   }
               }
            });
     
            $('#thumbnails').carouFredSel({
            	
               auto: false,
    
                   items: {
                	   start: -2
                         }
            });
     
            $('#thumbnails img').click(function() {
            $('#carousel').trigger( 'slideTo', [ $('#carousel img[alt='+ $(this).attr( 'alt' ) +']') ] );
     
            }).css( 'cursor', 'pointer' );
     });