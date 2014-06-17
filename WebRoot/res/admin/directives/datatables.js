'use strict';

/* Directives */
var directives = angular.module('adminDirectives',[]);
	/**唯一验证*/
	directives.directive('data1Table', ['$http',function($http){
		return function(scope, element, attr, ctrl){
			require([
			         'dataTable'
			], function(){
				var type = attr.data1Table;
				gebo_datatbles[type].call(this, $(element));
			});
		};
	}]);
	
	//* calendar
	var gebo_datatbles = {
			
		dt_a: function(element) {
			element.dataTable({ 
                "sDom": "<'row'<'span6'<'dt_actions'>l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap_alt",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page"
                }
            });
		},
        dt_b: function(element) {
        	element.dataTable({
				"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
                "sScrollX": "100%",
                "sScrollXInner": '110%',
                "sPaginationType": "bootstrap",
                "bScrollCollapse": true 
            });
		},
		dt_c: function() {
                
            var aaData = [];
            for ( var i=1, len=1000 ; i<=len ; i++ ) {
                aaData.push( [ i, i, i, i, i ] );
            };
            
            $('#dt_c').dataTable({
				"sDom": "<'row'<'span6'><'span6'f>r>t<'row'<'span6'i><'span6'>S>",
                "sScrollY": "200px",
                "aaData": aaData,
                "bDeferRender": true
			});
            
            $('#fill_table').click(function(){
                var aaData = [];
                for ( var i=1, len=50000; i <= len; i++){
                    aaData.push( [ i, i, i, i, i ] );
                };
               
                $('#dt_c').dataTable({
                    "sDom": "<'row'<'span6'><'span6'f>r>t<'row'<'span6'i><'span6'>S>",
                    "sScrollY": "200px",
                    "aaData": aaData,
                    "bDestroy": true,
                    "bDeferRender": true
                });
                $(this).remove();
                $('#entries').html('50 000');
                $('.dataTables_scrollHeadInner').css({'height':'34px','top':'0'});
            });
            
		},
		dt_d: function() {
		
			var oTable = $('#dt_d').dataTable({
				"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
				"sPaginationType": "bootstrap"
			});
			
			$('#dt_d_nav').on('click','li input',function(){
				fnShowHide( $(this).val() );
			});
		},
		dt_e: function(){
			if($('#dt_e').length) {

				var oTable;
 
				oTable = $('#dt_e').dataTable( {
					"bProcessing": true,
					"bServerSide": true,
                    "sPaginationType": "bootstrap",
                    "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
					"sAjaxSource": "lib/datatables/server_side.php",
					"aoColumns": [
						{ "sClass": "center", "bSortable": false },
						null,
						null,
						null,
						{ "sClass": "center" },
						{ "sClass": "center" }
					],
					"aaSorting": [[1, 'asc']]
				} );
				
                 
				$('#dt_e').on('click','tbody td img', function () {
					var nTr = $(this).parents('tr')[0];
					if ( oTable.fnIsOpen(nTr) )
					{
						/* This row is already open - close it */
						this.src = "img/details_open.png";
						oTable.fnClose( nTr );
					} else {
						/* Open this row */
						this.src = "img/details_close.png";
						oTable.fnOpen( nTr, fnFormatDetails(nTr), 'details' );
					}
				} );

			}
		}
	};
	
	function fnShowHide( iCol ) {
		/* Get the DataTables object again - this is not a recreation, just a get of the object */
		var oTable = $('#dt_d').dataTable();
		 
		var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
		oTable.fnSetColumnVis( iCol, bVis ? false : true );
	};
	
	/* Formating function for row details */
	function fnFormatDetails ( nTr ){
		var aData = oTable.fnGetData( nTr );
		var sOut = '<table cellpadding="5" cellspacing="0" border="0" class="table table-bordered" >';
		sOut += '<tr><td>Rendering engine:</td><td>'+aData[2]+' '+aData[5]+'</td></tr>';
		sOut += '<tr><td>Link to source:</td><td>Could provide a link here</td></tr>';
		sOut += '<tr><td>Extra info:</td><td>And any further details here (images etc)</td></tr>';
		sOut += '</table>';
		 
		return sOut;
	}