Ext.define('inlineEditor.Store.Shoes', {
			extend : 'Ext.data.Store',
			requires : ['inlineEditor.Model.Shoes'],
			model : 'inlineEditor.Model.Shoes',
			autoSync : false,
			autoLoad : true,
			storeId : 'Shoes'
		});