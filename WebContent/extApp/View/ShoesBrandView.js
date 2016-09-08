Ext.define('inlineEditor.View.ShoesBrandView', {

			extend : 'Ext.panel.Panel',
			requires : ['inlineEditor.View.BrandGrid',
					'inlineEditor.View.ShoesGrid'],
			alias : 'widget.ShoesBrandView',
			// constructor : function(config) {
			// // this.initConfig(config);
			// return this.callParent(arguments);
			// },
			title : 'Brand & Shoes Information',
			resizable : false,
			authHeight : true,
			collapsible : true,
			bodyPadding : '5 5 5 5',
			buttonAlign : 'center',
			useBorder : false,
			initComponent : function() {
				Ext.apply(this, {
							layout : {
								type : 'hbox'
							},
							items : [{
										xtype : 'BrandGrid'
									}, {
										xtype : 'ShoesGrid'
									}]
//							buttons : [{
//										text : 'Create',
//										itemId : 'btnCreate'
//									}, {
//										text : 'Load Data',
//										itemId : 'btnLoad'
//									}, {
//										text : 'Save',
//										itemId : 'btnSave'
//									}, {
//										text : 'Delete',
//										itemId : 'btnDelete'
//									}]
						});

				this.callParent(arguments);
			}
		});