Ext.application({
			name : 'inlineEditor',
			appFolder : 'extApp',
			requires : ['Ext.container.Viewport'],
			controllers : ['inlineEditor.Controller.ShoesBrandController'],
			models : ['inlineEditor.Model.Brand', 'inlineEditor.Model.Shoes',
					'inlineEditor.Model.Country'],
			stores : ['inlineEditor.Store.Brand', 'inlineEditor.Store.Shoes',
					'inlineEditor.Store.Country'],
			views : ['inlineEditor.View.BrandGrid',
					'inlineEditor.View.ShoesBrandView',
					'inlineEditor.View.ShoesGrid'],

			launch : function() {
				Ext.create('Ext.container.Viewport', {
							layout : 'anchor',
							items : [{
										xtype : 'ShoesBrandView'
									}]
						});
			}
		})